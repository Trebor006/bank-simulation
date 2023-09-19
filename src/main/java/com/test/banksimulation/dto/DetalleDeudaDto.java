package com.test.banksimulation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleDeudaDto {
    private String nombre;
    private String apellido;
    private String identificacion;
    private List<DetalleDeuda> detalle;
}
