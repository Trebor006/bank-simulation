package com.test.banksimulation.controller;

import com.google.gson.Gson;
import com.test.banksimulation.dto.DetalleDeudaDto;
import com.test.banksimulation.dto.DeudaRequestDto;
import com.test.banksimulation.service.DeudaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("deudas")
public class DeudasController {

  @Autowired private DeudaService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Void> registrarDeuda(
      @RequestHeader("uuid") String uuid, @RequestBody DeudaRequestDto registrarDeuda) {

    log.info("uuid :" + uuid);
    service.registrarDeuda(registrarDeuda);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/byUser/{identificacion}")
  public ResponseEntity<DetalleDeudaDto> obtenerDeudasPorCliente(
      @RequestHeader("uuid") String uuid, @PathVariable String identificacion) {

    log.info("uuid :" + uuid + ", identificacion :: " + identificacion);

    DetalleDeudaDto detalleDeudaDto = service.obtenerDeudasPorCliente(identificacion);

    return ResponseEntity.ok(detalleDeudaDto);
  }
}
