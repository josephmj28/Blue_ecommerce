package org.example.ecommerceblue.repository;

import org.example.ecommerceblue.models.Orden;
import org.example.ecommerceblue.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {


    List<Orden> findByUsuario(Usuario usuario);
}
