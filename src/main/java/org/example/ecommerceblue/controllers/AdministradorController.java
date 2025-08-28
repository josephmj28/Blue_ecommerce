package org.example.ecommerceblue.controllers;


import org.example.ecommerceblue.models.Producto;
import org.example.ecommerceblue.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IProductoService productoService;
    @GetMapping("")
    public String home(Model model){

        List<Producto> productos=productoService.findAll();
        model.addAttribute("productos",productos);

        return "administrador/home";
    }

}

