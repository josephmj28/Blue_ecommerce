package org.example.Epoli.controllers;
import jakarta.servlet.http.HttpSession;
import org.example.Epoli.models.Detalleorden;
import org.example.Epoli.models.Orden;
import org.example.Epoli.models.Producto;
import org.example.Epoli.models.Usuario;
import org.example.Epoli.service.IDetalleOrdenService;
import org.example.Epoli.service.IOrdenService;
import org.example.Epoli.service.IProductoService;
import org.example.Epoli.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Double cantidad, Model model){
        Detalleorden detalleOrden = new Detalleorden();
        Producto producto = new Producto();
        double sumaTotal = 0;
        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("El producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto=optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añada dos veces

        Integer idProducto=producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado){
            detalles.add(detalleOrden);
        }





        sumaTotal = detalles.stream().mapToDouble(dt-> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);


        return "usuario/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteCart(@PathVariable Integer id, Model model){

        //Lista nueva de prodcutos
        List<Detalleorden> ordenesNueva= new ArrayList<Detalleorden>();
        for (Detalleorden detalleOrden: detalles){
            if (detalleOrden.getProducto().getId() != id){
                ordenesNueva.add(detalleOrden);
            }
        }
        detalles=ordenesNueva;
        double sumaTotal=0;

        sumaTotal = detalles.stream().mapToDouble(dt-> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);


        return "usuario/carrito";
    }

    @GetMapping ("/getCart")
    public String getCart(Model model, HttpSession session){


        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        model.addAttribute("sesion",session.getAttribute("idusuario"));
        return "/usuario/carrito";
    }

    @GetMapping ("/verOrden")
    public String verOrden(Model model, HttpSession session){


        Usuario usuario = usuarioService.findById(1).get();


        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario",usuario);
        return "/usuario/resumenorden";
    }

    //guardar la orden
    @GetMapping("/saveOrder")
    public String saveOrder(Model model, HttpSession session){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        Usuario usuario = usuarioService.findById(1).get();
        orden.setUsuario(usuario);
        ordenService.save(orden);

        for(Detalleorden dt:detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

        orden = new Orden();
        detalles.clear();


        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(@RequestParam String nombre, Model model){
        log.info("Nombre del producto: {}", nombre);
        List<Producto> productos = productoService.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);

        return "usuario/usuario";
    }
}