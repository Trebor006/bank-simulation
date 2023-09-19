package com.test.banksimulation.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "identificacion", nullable = false, unique = true, length = 20)
  private String identificacion;

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "apellido", nullable = false, length = 100)
  private String apellido;

  @Column(name = "correo", nullable = false, length = 200)
  private String correo;

  @Column(name = "telefono", nullable = false, length = 20)
  private String telefono;
}
