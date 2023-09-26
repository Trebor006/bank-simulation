package com.test.banksimulation.controller;

import com.test.banksimulation.dto.*;
import com.test.banksimulation.service.UsuarioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
public class UsuariosController {

  @Autowired private UsuarioService service;

  @GetMapping
  public ResponseEntity<List<UsuarioDto>> obtenerDeudasPorCliente() {
    log.info("obtenerDeudasPorCliente");

    return ResponseEntity.ok(service.obtenerUsuarios());
  }
}
