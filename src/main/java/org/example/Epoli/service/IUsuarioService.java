package org.example.Epoli.service;

import org.example.Epoli.models.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findById(Integer id);
    Usuario save(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
    Usuario registrarUsuario(Usuario usuario);


}
