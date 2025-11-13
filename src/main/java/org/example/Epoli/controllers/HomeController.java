package org.example.Epoli.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.Epoli.models.Detalleorden;
import org.example.Epoli.models.Orden;
import org.example.Epoli.models.Producto;
import org.example.Epoli.service.IDetalleOrdenService;
import org.example.Epoli.service.IOrdenService;
import org.example.Epoli.service.IProductoService;
import org.example.Epoli.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    @Controller
    @RequestMapping("/")
    public class HomeController {

        private final Logger log= LoggerFactory.getLogger(HomeController.class);

        @Autowired
        private IProductoService productoService;

        @Autowired
        private IUsuarioService usuarioService;

        @Autowired
        private IOrdenService ordenService;

        @Autowired
        private IDetalleOrdenService detalleOrdenService;


        List<Detalleorden> detalles= new ArrayList<Detalleorden>();

        //alamcenar detalles de la orden
        Orden orden = new Orden();

        @GetMapping("")
        public String home(Model model, HttpSession session){

            log.info("Sesion del usuario {}", session.getAttribute("idusuario"));
            model.addAttribute("productos",productoService.findAll());

            model.addAttribute("sesion",session.getAttribute("idusuario"));

            return "usuario/usuario";
        }

        @GetMapping("productohome/{id}")
        public String productoHome(@PathVariable Integer id, Model model){

            log.info("id enviado como parametro{}", id);
            Producto producto = new Producto();
            Optional<Producto> productoOptional= productoService.get(id);
            producto = productoOptional.get();

            model.addAttribute("producto", producto);

            return "usuario/productohome";
        }
}
