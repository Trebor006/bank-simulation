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
@Table(name = "deuda")
public class Deuda {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @Column(name = "monto_deuda", nullable = false)
  private BigDecimal montoDeuda;

  @Column(name = "fecha_vencimiento", nullable = false)
  private Date fechaVencimmiento;
}
