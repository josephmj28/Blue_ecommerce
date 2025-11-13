package org.example.Epoli.repository;

import org.example.Epoli.models.Orden;
import org.example.Epoli.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {


    List<Orden> findByUsuario(Usuario usuario);
}
