package com.test.banksimulation.controller;

import com.test.banksimulation.components.response.ApiResponse;
import com.test.banksimulation.components.response.ErrorApiResponseService;
import com.test.banksimulation.components.response.SuccessApiResponseService;
import com.test.banksimulation.dto.DetallePagoResponse;
import com.test.banksimulation.dto.PagoDto;
import com.test.banksimulation.service.PagoService;
import java.util.Optional;
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
  @Autowired private SuccessApiResponseService responseService;
  @Autowired private ErrorApiResponseService errorApiResponseService;

  @PostMapping
  public ResponseEntity<ApiResponse> registrarPago(
      @RequestHeader("uuid") String uuid, @RequestBody PagoDto pagoDto) {
    log.info("UUID: " + uuid);
    log.info("Procesando Pago :" + pagoDto.getDeudaId());
    Optional<DetallePagoResponse> detallePagoResponse = service.registrarPago(pagoDto, uuid);
    if (detallePagoResponse.isPresent()) {
      return ResponseEntity.ok(responseService.createSuccessResponse(detallePagoResponse.get()));
    }
    return ResponseEntity.ok(
        errorApiResponseService.getObjectApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, ""));
  }
}
