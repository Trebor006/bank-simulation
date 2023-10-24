package com.test.banksimulation.controller;

import com.test.banksimulation.components.response.ErrorApiResponseService;
import com.test.banksimulation.components.response.SuccessApiResponseService;
import com.test.banksimulation.dto.PagoDto;
import com.test.banksimulation.service.PagoService;

import com.test.banksimulation.service.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("pagos")
public class PagosController {

  @Autowired private QueueService queueService;
  @Autowired private PagoService service;
  @Autowired private SuccessApiResponseService responseService;
  @Autowired private ErrorApiResponseService errorApiResponseService;

  @PostMapping
  public ResponseEntity registrarPago(
      @RequestHeader("uuid") String uuid,
      @RequestHeader("callbackUri") String callbackUri,
      @RequestBody PagoDto pagoDto) {
    log.info("UUID: " + uuid);
    log.info("Procesando Pago :" + pagoDto.getDeudaId());
    queueService.storeToQueue(pagoDto, uuid, callbackUri);

    return ResponseEntity.ok().build();
  }
}
