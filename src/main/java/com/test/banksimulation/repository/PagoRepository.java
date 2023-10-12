package com.test.banksimulation.repository;

import com.test.banksimulation.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
  @Query("Select p from Pago p where p.deuda.usuario.id = :usuarioId")
  List<Pago> findAllByUsuarioId(Long usuarioId);

  Optional<Pago> findByUuid(String uuid);
}
