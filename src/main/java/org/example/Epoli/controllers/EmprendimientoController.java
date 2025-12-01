package org.example.Epoli.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.Epoli.models.Emprendimiento;
import org.example.Epoli.models.Usuario;
import org.example.Epoli.service.IEmprendimientoService;
import org.example.Epoli.service.IUsuarioService;
import org.example.Epoli.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/emprendimiento")
public class EmprendimientoController {

    @Autowired
    private IEmprendimientoService emprendimientoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private UploadFileService uploadFileService;

    // Mostrar lista de emprendimientos (opcional)
    @GetMapping("")
    public String show(Model model){
        model.addAttribute("emprendimientos", emprendimientoService.getAll());
        model.addAttribute("emprendimiento", new Emprendimiento());
        return "admin/emprendimiento/form";
    }

    // Abrir formulario para crear
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("emprendimiento", new Emprendimiento());
        return "admin/emprendimiento/form"; //  formulario Thymeleaf
    }

    // Guardar nuevo emprendimiento
    @PostMapping("/save")
    public String save(Emprendimiento e, MultipartFile file, HttpSession session) throws IOException {

        // Usuario fijo para pruebas, después lo puedes sacar de sesión
        Usuario u = usuarioService.findById(1).orElseThrow();
        e.setOwner(u);

        // Subir imagen si se cargó
        if (!file.isEmpty()) {
            String nombreImagen = uploadFileService.saveImage(file);
            e.setImagenUrl(nombreImagen);
        }

        emprendimientoService.save(e);

        // Redirigir al mismo formulario para poder agregar productos después
        return "redirect:/admin/emprendimiento/edit/" + e.getId();
    }

    // Abrir formulario para editar
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Emprendimiento e = emprendimientoService.getById(id).orElseThrow();
        model.addAttribute("emprendimiento", e);
        return "admin/emprendimiento/form";
    }

    // Actualizar emprendimiento
    @PostMapping("/update")
    public String update(Emprendimiento e, MultipartFile file) throws IOException {
        Emprendimiento db = emprendimientoService.getById(e.getId()).orElseThrow();

        e.setOwner(db.getOwner());

        if (!file.isEmpty()) {
            String nombreImagen = uploadFileService.saveImage(file);
            e.setImagenUrl(nombreImagen);
        }

        emprendimientoService.save(e);

        return "redirect:/admin/emprendimiento/edit/" + e.getId();
    }

    // Eliminar emprendimiento
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        emprendimientoService.delete(id);
        return "redirect:/admin/emprendimiento";
    }

    // Ir al CRUD de productos de este emprendimiento
    @GetMapping("/{id}/productos")
    public String productos(@PathVariable Long id, Model model){
        Emprendimiento e = emprendimientoService.getById(id).orElseThrow();
        model.addAttribute("productos", e.getProductos());
        model.addAttribute("emprendimiento", e);
        return "productos/show"; // Reutilizamos la vista de productos
    }

}
