package com.globalSolution.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.globalSolution.api.models.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String username);

}
