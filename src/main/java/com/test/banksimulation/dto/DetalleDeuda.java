package com.test.banksimulation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleDeuda {
    private Long deudaId;
    private Double montoDeuda;
    private Double montoPagos;
}

