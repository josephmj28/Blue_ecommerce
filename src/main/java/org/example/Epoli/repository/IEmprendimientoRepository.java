package org.example.Epoli.repository;

import org.example.Epoli.models.Emprendimiento;
import org.example.Epoli.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {
    List<Emprendimiento> findByOwnerId(Long ownerId);
}
