package com.test.banksimulation.service;

import com.test.banksimulation.dto.DetalleDeuda;
import com.test.banksimulation.dto.DetalleDeudaDto;
import com.test.banksimulation.dto.DeudaRequestDto;
import com.test.banksimulation.entity.Deuda;
import com.test.banksimulation.entity.Pago;
import com.test.banksimulation.entity.Usuario;
import com.test.banksimulation.repository.DeudaRepository;
import com.test.banksimulation.repository.PagoRepository;
import com.test.banksimulation.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.test.banksimulation.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeudaService {

  @Autowired private DeudaRepository deudaRepository;
  @Autowired private PagoRepository pagoRepository;
  @Autowired private UsuarioRepository usuarioRepository;

  public void registrarDeuda(DeudaRequestDto registrarDeuda) {
    Usuario usuario =
        usuarioRepository
            .findByIdentificacion(registrarDeuda.getIdentificacion())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Deuda deuda =
        Deuda.builder()
            .usuario(usuario)
            .montoDeuda(registrarDeuda.getMonto())
            .fechaVencimmiento(DateUtils.toDate(registrarDeuda.getFechaVencimmiento()))
            .build();
    deudaRepository.save(deuda);
  }

  public DetalleDeudaDto obtenerDeudasPorCliente(String identificacion) {
    Usuario usuario =
        usuarioRepository
            .findByIdentificacion(identificacion)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    List<Deuda> deudas = deudaRepository.findAllByUsuarioId(usuario.getId());
    List<Pago> pagos = pagoRepository.findAllByUsuarioId(usuario.getId());

    return DetalleDeudaDto.builder()
        .identificacion(identificacion)
        .nombre(usuario.getNombre())
        .apellido(usuario.getApellido())
        .detalle(mapDeudas(deudas, pagos))
        .build();
  }

  private List<DetalleDeuda> mapDeudas(List<Deuda> deudas, List<Pago> pagos) {
    List<DetalleDeuda> result = new ArrayList<>();
    for (Deuda deuda : deudas) {
      result.add(
          DetalleDeuda.builder()
              .deudaId(deuda.getId())
              .montoDeuda(deuda.getMontoDeuda().doubleValue())
              .montoPagos(getPagoPorDeuda(deuda.getId(), pagos))
              .build());
    }

    return result;
  }

  private Double getPagoPorDeuda(Long id, List<Pago> pagos) {
    return pagos.stream()
        .filter(pago -> Objects.equals(pago.getDeuda().getId(), id))
        .map(Pago::getMontoPago)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .doubleValue();
  }
}
