package org.example.Epoli.controllers;

import org.example.Epoli.models.Usuario;
import org.example.Epoli.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth2/register"; // tu formulario de registro
    }

    // Procesar registro
    @PostMapping("/register")
    public String register(@ModelAttribute Usuario usuario, @RequestParam("tipo") String tipo) {
        // Asignar rol según tipo
        if ("Emprendedor".equalsIgnoreCase(tipo)) {
            usuario.setTipo("ADMIN"); // ADMIN para emprendedor
        } else {
            usuario.setTipo("USER");  // USER normal
        }

        // Guardar usuario (contraseña ya codificada en el service)
        usuarioService.registrarUsuario(usuario);

        // Auto-login solo para usuarios tipo USER
        if ("USER".equalsIgnoreCase(usuario.getTipo())) {
            // Creamos token de autenticación con AuthenticationManager
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Redirigir al main ya logueado
            return "redirect:usuario/usuario";
        }

        // Para ADMIN (emprendedor), redirige al login
        return "redirect:/login";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth2/login"; // tu formulario de login
    }
}
