package com.test.banksimulation.repository;

import com.test.banksimulation.entity.Deuda;
import com.test.banksimulation.entity.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeudaRepository extends JpaRepository<Deuda, Long> {

    @Query("select d from Deuda d where d.usuario.id = :usuarioId and d.status = :status")
    List<Deuda> findAllByUsuarioId(Long usuarioId, StatusEnum status);
}
