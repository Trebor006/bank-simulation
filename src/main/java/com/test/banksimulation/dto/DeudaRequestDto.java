package com.test.banksimulation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeudaRequestDto {
  private String identificacion;
  private BigDecimal monto;
  private String fechaVencimmiento;
}
