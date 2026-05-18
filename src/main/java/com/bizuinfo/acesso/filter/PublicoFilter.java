package com.bizuinfo.acesso.filter;

import com.bizuinfo.usuario.model.Usuario;

import com.bizuinfo.web.Paginas;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/publico/*")
public class PublicoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        Usuario usuario = null;

        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuario");
        }

        if (usuario != null) {

            String destino = switch (usuario.getRole()) {
                case ADMIN ->
                    Paginas.DASHBOARD_ADMIN;
                case GERENTE ->
                    Paginas.DASHBOARD_GERENTE;
                default ->
                    Paginas.DASHBOARD;
            };

            res.sendRedirect(req.getContextPath() + destino);
            return;
        }

        chain.doFilter(request, response);
    }
}