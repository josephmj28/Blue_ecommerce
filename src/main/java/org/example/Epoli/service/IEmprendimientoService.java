package org.example.Epoli.service;

import org.example.Epoli.models.Emprendimiento;

import java.util.List;
import java.util.Optional;

public interface IEmprendimientoService {

    List<Emprendimiento> getAll();
    Optional<Emprendimiento> getById(Long id);
    Emprendimiento save(Emprendimiento e);
    void delete(Long id);
    List<Emprendimiento> getByOwner(Long ownerId);
}
