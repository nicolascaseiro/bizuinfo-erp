package com.bizuinfo.acesso.bean;

import com.bizuinfo.usuario.model.Usuario;
import com.bizuinfo.web.Paginas;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;

import static com.bizuinfo.usuario.model.Role.ADMIN;
import static com.bizuinfo.usuario.model.Role.GERENTE;

@Named
@SessionScoped // Agora ele persiste enquanto a sessão durar
public class SessaoBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Usuario usuarioLogado;

    public void login(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void logout() {
        this.usuarioLogado = null;
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public String getDashboard() {
        return switch (usuarioLogado.getRole()) {

            case ADMIN -> Paginas.DASHBOARD_ADMIN
                    + "?faces-redirect=true";

            case GERENTE -> Paginas.DASHBOARD_GERENTE
                    + "?faces-redirect=true";

            default -> Paginas.DASHBOARD_FUNCIONARIO
                    + "?faces-redirect=true";
        };
    }
}