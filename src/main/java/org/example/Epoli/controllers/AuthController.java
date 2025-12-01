package org.example.Epoli.controllers;

import org.example.Epoli.models.Usuario;
import org.example.Epoli.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth2/register";
    }

    // Procesar registro
    @PostMapping("/register")
    public String register(@ModelAttribute Usuario usuario, @RequestParam("tipo") String tipo) {
        // Si elige emprendedor, se asigna rol ADMIN
        if ("Emprendedor".equalsIgnoreCase(tipo)) {
            usuario.setTipo("EMPRENDEDOR");
        } else {
            usuario.setTipo("USER");
        }


        usuarioService.registrarUsuario(usuario);

        return "redirect:/login";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth2/login"; // ruta thymeleaf: templates/auth/login.html
    }
}
