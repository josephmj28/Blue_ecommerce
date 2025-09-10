package org.example.ecommerceblue.repository;

import org.example.ecommerceblue.models.Detalleorden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<Detalleorden, Integer> {
}