package org.example.Epoli.service;

import org.example.Epoli.models.Emprendimiento;
import org.example.Epoli.repository.IEmprendimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmprendimientoServiceImpl implements IEmprendimientoService {

    private final IEmprendimientoRepository repository;

    public EmprendimientoServiceImpl(IEmprendimientoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Emprendimiento> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Emprendimiento> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Emprendimiento save(Emprendimiento e) {
        return repository.save(e);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Emprendimiento> getByOwner(Long id) {
        return repository.findByOwnerId(id);
    }


}
