package org.example.Epoli.controllers;




import jakarta.servlet.http.HttpSession;
import org.example.Epoli.models.Orden;
import org.example.Epoli.models.Usuario;
import org.example.Epoli.service.IOrdenService;
import org.example.Epoli.service.IUsuarioService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @GetMapping("/registro")
    public String registro(){
        return "/usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        logger.info("Usuario registro: {}", usuario);
        usuario.setTipo("USER");
        usuarioService.save(usuario);


        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){
        logger.info("Accesos: {}", usuario);

        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        // logger.info("Usuario de db: {}", user.get());

        if (user.isPresent()){
            session.setAttribute("idusuario",user.get().getId());
            if (user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";

            }
            else {
                return "redirect:/";
            }
        }
        else {
            logger.info("Usuario no existe");
        }


        return "redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        List<Orden> ordenes = ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }
    @GetMapping("/detalles/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session,Model model){

        logger.info("id de la orden: {}", id);
        Optional<Orden> orden = ordenService.findById(id);
        model.addAttribute("detalles", orden.get().getDetalleOrden());

        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerraSesion(HttpSession session){
        session.removeAttribute("idusuario");
        return "redirect:/";
    }
}

