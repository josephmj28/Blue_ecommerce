package org.example.ecommerceblue.service;

import org.example.ecommerceblue.models.Orden;
import org.example.ecommerceblue.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {

    List<Orden> findAll();
    Optional<Orden> findById(Integer id);
    Orden save(Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);

}
