package com.test.banksimulation.service;

import com.test.banksimulation.dto.DetallePagoDeudaResponse;
import com.test.banksimulation.dto.DetallePagoResponse;
import com.test.banksimulation.dto.PagoDto;
import com.test.banksimulation.entity.Deuda;
import com.test.banksimulation.entity.Pago;
import com.test.banksimulation.entity.enums.StatusEnum;
import com.test.banksimulation.repository.DeudaRepository;
import com.test.banksimulation.repository.PagoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PagoService {

  private final DeudaRepository deudaRepository;
  private final PagoRepository pagoRepository;

  @Autowired RestTemplate restTemplate;

  public Optional<DetallePagoResponse> procesarPago(PagoDto pagoDto, String uuid) {
    Optional<Pago> pagoStored = pagoRepository.findByUuid(uuid);
    if (pagoStored.isPresent()) {
      return responseDetallePago(pagoStored.get());
    }

    Deuda deuda = deudaRepository.findById(pagoDto.getDeudaId()).orElseThrow();
    if (deuda.getSaldo().doubleValue() >= pagoDto.getMonto()) {
      log.info("Saldo Detectado :" + deuda.getSaldo().doubleValue());

      BigDecimal montoPago =
          BigDecimal.valueOf(pagoDto.getMonto()).setScale(2, RoundingMode.HALF_UP);
      Pago pago =
          Pago.builder().deuda(deuda).fechaPago(new Date()).montoPago(montoPago).uuid(uuid).build();

      pagoRepository.save(pago);

      deuda.setSaldo(deuda.getSaldo().subtract(montoPago).setScale(2, RoundingMode.HALF_UP));
      if (deuda.getSaldo().doubleValue() == 0) {
        deuda.setStatus(StatusEnum.PAID);
      }

      deudaRepository.save(deuda);

      log.info(
          "Deuda Actualizada " + deuda.getStatus() + ": saldo : " + deuda.getSaldo().doubleValue());
      return responseDetallePago(pago, deuda);
    }

    return Optional.empty();
  }

  private Optional<DetallePagoResponse> responseDetallePago(Pago pago) {
    return responseDetallePago(pago, pago.getDeuda());
  }

  private Optional<DetallePagoResponse> responseDetallePago(Pago pago, Deuda deuda) {
    return Optional.of(
        DetallePagoResponse.builder()
            .pagoId(pago.getId())
            .uuid(pago.getUuid())
            .monto(pago.getMontoPago())
            .detalleDeuda(responseDetalleDeuda(deuda))
            .build());
  }

  private DetallePagoDeudaResponse responseDetalleDeuda(Deuda deuda) {
    return DetallePagoDeudaResponse.builder()
        .deudaId(deuda.getId())
        .monto(deuda.getMontoDeuda())
        .saldo(deuda.getSaldo())
        .build();
  }

  public void notifyPayment(
      String notificationUri, String uuid, DetallePagoResponse detallePagoResponse) {
    log.info("notifyPayment.........");
    HttpHeaders headers = new HttpHeaders();
    headers.set("uuid", uuid);
    HttpEntity<DetallePagoResponse> entity = new HttpEntity<>(detallePagoResponse, headers);
    restTemplate.postForObject(notificationUri, entity, Void.class);
  }
}
