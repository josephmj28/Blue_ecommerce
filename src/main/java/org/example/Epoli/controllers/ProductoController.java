package org.example.Epoli.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.Epoli.models.Producto;
import org.example.Epoli.models.Usuario;
import org.example.Epoli.service.IProductoService;
import org.example.Epoli.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService productoService;

    @Autowired
    private UploadFileService uploadFileService;

    // ===========================
    // MOSTRAR PRODUCTOS
    // ===========================
    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }

    // ===========================
    // FORM CREAR PRODUCTO
    // ===========================
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    // ===========================
    // GUARDAR PRODUCTO
    // ===========================
    @PostMapping("/save")
    public String save(Producto producto, MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Guardando producto: {}", producto);

        // Usuario fijo temporal (luego se cambia por sesión)
        Usuario u = new Usuario(1,"","","","","","","");
        producto.setUsuario(u);

        if (!file.isEmpty()){
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        productoService.save(producto);
        return "redirect:/productos";
    }

    // ===========================
    // FORM EDITAR PRODUCTO
    // ===========================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Optional<Producto> optionalProducto = productoService.get(id);
        Producto producto = optionalProducto.get();

        model.addAttribute("producto", producto);
        return "productos/edit";
    }

    // ===========================
    // ACTUALIZAR PRODUCTO
    // ===========================
    @PostMapping("/update")
    public String update(Producto producto, MultipartFile file) throws IOException {

        Producto pDB = productoService.get(producto.getId()).get();
        producto.setUsuario(pDB.getUsuario());

        if (!file.isEmpty()){
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        } else {
            producto.setImagen(pDB.getImagen());
        }

        productoService.update(producto);
        return "redirect:/productos";
    }

    // ===========================
    // ELIMINAR PRODUCTO
    // ===========================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        productoService.delete(id);
        return "redirect:/productos";
    }
}
