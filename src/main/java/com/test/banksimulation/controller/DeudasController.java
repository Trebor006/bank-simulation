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
@RequestMapping("deuda")
public class DeudasController {

  @Autowired
  private DeudaService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Void> registrarDeuda(@RequestBody DeudaRequestDto registrarDeuda) {

    service.registrarDeuda(registrarDeuda);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/byUser/{identificacion}")
  public ResponseEntity<DetalleDeudaDto> obtenerDeudasPorCliente(@PathVariable String identificacion) {
    log.info("identificacion :: " + identificacion);

    DetalleDeudaDto detalleDeudaDto = service.obtenerDeudasPorCliente(identificacion);

    return ResponseEntity.ok(detalleDeudaDto);
  }
}
