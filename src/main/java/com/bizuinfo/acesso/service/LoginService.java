package com.bizuinfo.acesso.service;

import com.bizuinfo.usuario.dao.UsuarioDAO;
import com.bizuinfo.usuario.model.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@ApplicationScoped
public class LoginService {

    @Inject
    private UsuarioDAO usuarioDAO;

    public Usuario autenticar(String email, String senha) {

        Optional<Usuario> opt = usuarioDAO.buscarPorEmail(email);

        if (opt.isEmpty()) {
            return null;
        }

        Usuario usuario = opt.get();

        boolean senhaCorreta = BCrypt.checkpw(senha, usuario.getSenha());

        if (!senhaCorreta) {
            return null;
        }

        if (!usuario.getEmailVerificado()) {
            return null;
        }

        return usuario;
    }
}