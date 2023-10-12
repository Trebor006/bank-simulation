package com.test.banksimulation.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetallePagoDeudaResponse {
  private Long deudaId;
  private BigDecimal monto;
  private BigDecimal saldo;
}
