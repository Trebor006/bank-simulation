package com.test.banksimulation.service;

import com.test.banksimulation.dto.PagoDto;
import com.test.banksimulation.entity.Deuda;
import com.test.banksimulation.entity.Pago;
import com.test.banksimulation.repository.DeudaRepository;
import com.test.banksimulation.repository.PagoRepository;
import com.test.banksimulation.repository.UsuarioRepository;
import com.test.banksimulation.util.DateUtils;
import liquibase.pro.packaged.D;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PagoService {

  @Autowired private DeudaRepository deudaRepository;
  @Autowired private PagoRepository pagoRepository;
  @Autowired private UsuarioRepository usuarioRepository;

  public void registrarPago(PagoDto pagoDto) {

    Deuda deuda = Deuda.builder().id(pagoDto.getDeudaId()).build();
    Pago pago =
        Pago.builder()
            .deuda(deuda)
            .fechaPago(new Date())
            .montoPago(BigDecimal.valueOf(pagoDto.getMonto()))
            .build();

    pagoRepository.save(pago);
  }
}
