package com.test.banksimulation.service;

import com.google.gson.Gson;
import com.test.banksimulation.dto.DetallePagoResponse;
import com.test.banksimulation.dto.PagoDto;
import com.test.banksimulation.entity.Queue;
import com.test.banksimulation.entity.enums.QueueNotificationStatusEnum;
import com.test.banksimulation.entity.enums.QueueStatusEnum;
import com.test.banksimulation.repository.QueueRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService {

  private final QueueRepository queueRepository;
  private final PagoService pagoService;

  @Value("${queue.max.limit}")
  private int maxResults;

  @Transactional
  public void storeToQueue(Object data, String uuid, String callbackUri) {
    Queue queue =
        Queue.builder()
            .uuid(uuid)
            .data(new Gson().toJson(data))
            .status(QueueStatusEnum.PENDING)
            .notificationStatus(QueueNotificationStatusEnum.PENDING)
            .fecha(new Date())
            .notificationUri(callbackUri)
            .intentos(0)
            .build();

    queueRepository.save(queue);
  }

  @Transactional
  public void processPayment() {
    Pageable pageable = PageRequest.of(0, maxResults);

    List<Queue> firstPending = queueRepository.findAllPending(QueueStatusEnum.PENDING, pageable);

    for (Queue queue : firstPending) {
      PagoDto pagoDto = new Gson().fromJson(queue.getData(), PagoDto.class);

      Optional<DetallePagoResponse> detallePagoResponse =
          pagoService.procesarPago(pagoDto, queue.getUuid());

      queue.setIntentos(queue.getIntentos() + 1);

      if (detallePagoResponse.isPresent()) {
        updateQueue(queue, detallePagoResponse);
        processNotify(queue, detallePagoResponse.get());
      } else {
        updateWhenNotProcessed(queue);
      }
    }
  }

  private void updateWhenNotProcessed(Queue queue) {
    if (queue.getIntentos() == 3) {
      queue.setStatus(QueueStatusEnum.FAILED);
    }
    queueRepository.save(queue);
  }

  private void updateQueue(Queue queue, Optional<DetallePagoResponse> detallePagoResponse) {
    if (detallePagoResponse.isPresent()) {
      queue.setStatus(QueueStatusEnum.PROCESSED);
      queue.setResponseData(new Gson().toJson(detallePagoResponse.get()));
    } else {

    }

    queueRepository.save(queue);
  }

  @Async
  public void processNotify(Queue queue, DetallePagoResponse detallePagoResponse) {
    try {
      pagoService.notifyPayment(queue.getNotificationUri(), queue.getUuid(), detallePagoResponse);
      queue.setNotificationStatus(QueueNotificationStatusEnum.NOTIFIED);
      queueRepository.save(queue);
    } catch (Exception e) {
      log.info("Error notificando....");
    }
  }

  public void processNotify() {
    Pageable pageable = PageRequest.of(0, maxResults);

    List<Queue> queueToNotify =
        queueRepository.findAllToNotify(QueueNotificationStatusEnum.PENDING, pageable);

    for (Queue queue : queueToNotify) {
      DetallePagoResponse detallePagoResponse =
          new Gson().fromJson(queue.getResponseData(), DetallePagoResponse.class);

      processNotify(queue, detallePagoResponse);
    }
  }
}
