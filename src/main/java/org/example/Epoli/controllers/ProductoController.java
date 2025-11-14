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


    @GetMapping("/template")
    public void descargarTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=plantilla.csv");

        PrintWriter writer = response.getWriter();
        writer.println("id,nombre,descripcion,cantidad,precio"); // encabezados
        writer.flush();
        writer.close();
    }

    @PostMapping("/upload")
    public String subirCsv(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "El archivo está vacío");
            return "productos/show";
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { // Saltar cabecera
                    firstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length < 4) {
                    LOGGER.warn("Fila inválida en CSV: {}", Arrays.toString(fields));
                    continue;
                }

                Producto p = new Producto();
                p.setNombre(fields[0].trim());
                p.setDescripcion(fields[1].trim());
                p.setUbicacion(fields[2].trim());
                p.setPrecio(Double.parseDouble(fields[3].trim()));


                Usuario u = new Usuario(1,"","","","","","","");
                p.setUsuario(u);

                LOGGER.info("Guardando producto desde CSV: {}", p);

                productoService.save(p);
            }

            return "redirect:/productos";

        } catch (Exception e) {
            model.addAttribute("error", "Error procesando el archivo: " + e.getMessage());
            return "productos/show";
        }
    }






}

