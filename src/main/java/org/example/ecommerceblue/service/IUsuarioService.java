package org.example.ecommerceblue.service;

import org.example.ecommerceblue.models.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findById(Integer id);
    Usuario save(Usuario usuario);
    Optional<Usuario> findByEmail(String email);


}
