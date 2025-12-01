package org.example.Epoli.service;

import org.example.Epoli.models.Usuario;
import org.example.Epoli.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Asignar tipo según lo que venga del formulario
        if ("Emprendedor".equalsIgnoreCase(usuario.getTipo())) {
            usuario.setTipo("ADMIN");
        } else {
            usuario.setTipo("USER");
        }

        // Hash de contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }


}
