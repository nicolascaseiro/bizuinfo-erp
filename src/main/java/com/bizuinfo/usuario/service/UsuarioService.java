package com.bizuinfo.usuario.service;

import com.bizuinfo.usuario.dao.UsuarioDAO;
import com.bizuinfo.usuario.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UsuarioService {

    @Inject
    private UsuarioDAO uDAO;

    public boolean cadastrar(String nome, String email, String senha) {

        if (uDAO.buscarPorEmail(email).isPresent()) {
            return false;
        }

        Usuario usuario = new Usuario(
            nome,
            email,
            BCrypt.hashpw(senha, BCrypt.gensalt())
        );

        uDAO.salvar(usuario);

        return true;
    }
}