package com.test.banksimulation.service;

import com.test.banksimulation.dto.PagoDto;
import com.test.banksimulation.entity.Deuda;
import com.test.banksimulation.entity.Pago;
import com.test.banksimulation.entity.enums.StatusEnum;
import com.test.banksimulation.repository.DeudaRepository;
import com.test.banksimulation.repository.PagoRepository;
import com.test.banksimulation.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PagoService {

  @Autowired private DeudaRepository deudaRepository;
  @Autowired private PagoRepository pagoRepository;

  @Transactional
  public void registrarPago(PagoDto pagoDto) {
    Deuda deuda = deudaRepository.findById(pagoDto.getDeudaId()).orElseThrow();
    if (deuda.getSaldo().doubleValue() >= pagoDto.getMonto()) {
      log.info("Saldo Detectado :" + deuda.getSaldo().doubleValue());

      BigDecimal montoPago =
          BigDecimal.valueOf(pagoDto.getMonto()).setScale(2, RoundingMode.HALF_UP);
      Pago pago = Pago.builder().deuda(deuda).fechaPago(new Date()).montoPago(montoPago).build();

      pagoRepository.save(pago);

      deuda.setSaldo(deuda.getSaldo().subtract(montoPago).setScale(2, RoundingMode.HALF_UP));
      if (deuda.getSaldo().doubleValue() == 0) {
        deuda.setStatus(StatusEnum.PAID);
      }

      deudaRepository.save(deuda);

      log.info("Deuda Actualizada " + deuda.getStatus() + ": saldo : " + deuda.getSaldo().doubleValue());
    }
  }
}
