package com.bizuinfo.acesso.bean;

import com.bizuinfo.usuario.model.Usuario;
import com.bizuinfo.web.Paginas;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class SessaoBean {

    public String dashboard() {

        Usuario usuario = (Usuario) FacesContext
            .getCurrentInstance()
            .getExternalContext()
            .getSessionMap()
            .get("usuario");

        if (usuario == null) {
            return Paginas.LOGIN;
        }

        return switch (usuario.getRole()) {
            case ADMIN ->
                Paginas.DASHBOARD_ADMIN;
            case GERENTE ->
                Paginas.DASHBOARD_GERENTE;
            default ->
                Paginas.DASHBOARD;
        };
    }
}
