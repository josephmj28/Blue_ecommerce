package org.example.Epoli.repository;

import org.example.Epoli.models.Detalleorden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<Detalleorden, Integer> {
}