package br.com.curso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.curso.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
