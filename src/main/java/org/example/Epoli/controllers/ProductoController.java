package org.example.Epoli.controllers;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Epoli.models.Producto;
import org.example.Epoli.models.Usuario;
import org.example.Epoli.service.IProductoService;
import org.example.Epoli.service.IUsuarioService;
import org.example.Epoli.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;


    @Autowired
    private UploadFileService uploadFileService;


    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }


    @PostMapping("/save")
    public String save(Producto producto, MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Se esta guardando el prodcuto {}", producto);



        Usuario u = new Usuario(1,"","","","","","","");
        producto.setUsuario(u);

        if (producto.getId()==null){ //cuando se crea un producto
            String nombreImagen=uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        else{

        }

        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoService.get(id);
        producto = optionalProducto.get();

        LOGGER.info("producto buscado: {}", producto);
        model.addAttribute("producto",producto);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto,  MultipartFile file) throws IOException {

        Producto p=new Producto();
        p=productoService.get(producto.getId()).get();
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        Producto p = new Producto();
        p=productoService.get(id).get();



        productoService.delete(id);
        return "redirect:/productos";
    }



    }








