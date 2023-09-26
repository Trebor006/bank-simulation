package com.test.banksimulation.controller;

import com.test.banksimulation.dto.PagoDto;
import com.test.banksimulation.service.PagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("pagos")
public class PagosController {

  @Autowired private PagoService service;

  @PostMapping
  public ResponseEntity<Void> registrarPago(@RequestBody PagoDto pagoDto) {
    log.info("Procesando Pago :" + pagoDto.getDeudaId());
    service.registrarPago(pagoDto);

    return ResponseEntity.ok().build();
  }
}
