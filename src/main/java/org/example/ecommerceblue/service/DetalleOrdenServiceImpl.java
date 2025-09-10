package org.example.ecommerceblue.service;

import org.example.ecommerceblue.models.Detalleorden;
import org.example.ecommerceblue.repository.IDetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService {

    @Autowired
    private IDetalleOrdenRepository detalleOrdenRepository;

    @Override
    public Detalleorden save(Detalleorden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}

