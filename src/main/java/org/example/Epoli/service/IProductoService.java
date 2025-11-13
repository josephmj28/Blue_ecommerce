package org.example.Epoli.service;

import org.example.Epoli.models.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    public Producto save(Producto producto);
    public Optional<Producto> get(Integer id);
    public void update(Producto producto);
    public void delete(Integer id);
    public List<Producto> findAll();

}
