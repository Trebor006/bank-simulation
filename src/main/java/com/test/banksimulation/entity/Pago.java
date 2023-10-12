package com.test.banksimulation.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pago")
public class Pago {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "deuda_id")
  private Deuda deuda;

  @Column(name = "fecha_pago", nullable = false)
  private Date fechaPago;

  @Column(name = "monto_pago", nullable = false)
  private BigDecimal montoPago;

  @Column(name = "uuid", nullable = false)
  private String uuid;
}
