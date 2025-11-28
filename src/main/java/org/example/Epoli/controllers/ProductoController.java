package org.example.Epoli.controllers;

import org.example.Epoli.models.Emprendimiento;
import org.example.Epoli.models.Producto;
import org.example.Epoli.service.IEmprendimientoService;
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

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IEmprendimientoService emprendimientoService;

    @Autowired
    private UploadFileService uploadFileService;

    // Mostrar todos los productos (opcional, global)
    @GetMapping("")
    public String showAll(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }

    // Mostrar productos de un emprendimiento específico
    @GetMapping("/emprendimiento/{emprendimientoId}")
    public String showByEmprendimiento(@PathVariable Long emprendimientoId, Model model){
        Emprendimiento e = emprendimientoService.getById(emprendimientoId).orElseThrow(() -> new IllegalArgumentException("Emprendimiento no encontrado"));
        model.addAttribute("productos", e.getProductos());
        model.addAttribute("emprendimiento", e);
        return "productos/show";
    }

    // Formulario para crear un producto (ligado a un emprendimiento)
    @GetMapping("/create/{emprendimientoId}")
    public String create(@PathVariable Long emprendimientoId, Model model){
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        model.addAttribute("emprendimientoId", emprendimientoId);
        return "productos/create";
    }

    // Guardar producto ligado al emprendimiento
    @PostMapping("/save/{emprendimientoId}")
    public String save(@PathVariable Long emprendimientoId,
                       Producto producto,
                       @RequestParam("file") MultipartFile file) throws IOException {

        Emprendimiento e = emprendimientoService.getById(emprendimientoId).orElseThrow(() -> new IllegalArgumentException("Emprendimiento no encontrado"));
        producto.setEmprendimiento(e);

        if (producto.getId() == null && file != null && !file.isEmpty()) {
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        } else if (file != null && !file.isEmpty()) {
            // en edición: actualizar imagen si se sube una nueva
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        productoService.save(producto);
        return "redirect:/productos/emprendimiento/" + emprendimientoId;
    }

    // Formulario para editar producto (muestra emprendimientoId para redirección)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Producto producto = productoService.get(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        Long empId = producto.getEmprendimiento() != null ? producto.getEmprendimiento().getId() : null;
        model.addAttribute("emprendimientoId", empId);
        return "productos/edit";
    }

    // Actualizar producto (recibe id por formulario oculto o path)
    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         Producto producto,
                         @RequestParam("file") MultipartFile file) throws IOException {

        Producto db = productoService.get(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        producto.setId(id);
        producto.setEmprendimiento(db.getEmprendimiento()); // mantener vínculo

        if (file != null && !file.isEmpty()) {
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        productoService.update(producto);
        return "redirect:/productos/emprendimiento/" + db.getEmprendimiento().getId();
    }

    // Eliminar producto y volver al listado del emprendimiento
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Producto p = productoService.get(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        Long emprendimientoId = p.getEmprendimiento() != null ? p.getEmprendimiento().getId() : null;
        productoService.delete(id);
        if (emprendimientoId != null) {
            return "redirect:/productos/emprendimiento/" + emprendimientoId;
        } else {
            return "redirect:/productos";
        }
    }
}
