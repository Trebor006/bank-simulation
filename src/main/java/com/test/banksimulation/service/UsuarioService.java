package com.test.banksimulation.service;

import com.test.banksimulation.dto.UsuarioDto;
import com.test.banksimulation.entity.Usuario;
import com.test.banksimulation.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  @Autowired private UsuarioRepository usuarioRepository;

  public List<UsuarioDto> obtenerUsuarios() {
    List<Usuario> usuarios = usuarioRepository.findAll();

    return mapUsuarios(usuarios);
  }

  private List<UsuarioDto> mapUsuarios(List<Usuario> usuarios) {
    return usuarios.stream().map(this::mapUsuario).collect(Collectors.toList());
  }

  private UsuarioDto mapUsuario(Usuario usuario) {
    return UsuarioDto.builder()
        .identificacion(usuario.getIdentificacion())
        .build();
  }
}
