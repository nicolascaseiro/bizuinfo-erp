package com.bizuinfo.acesso.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

import com.bizuinfo.usuario.dao.UsuarioDAO;
import com.bizuinfo.usuario.model.Usuario;
import com.bizuinfo.web.Paginas;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

    private Usuario usuarioLogado;

    @Inject
    private UsuarioDAO usuarioDAO;

    public String entrar() {

        Optional<Usuario> usuarioOptional = usuarioDAO.buscarPorEmail(email);

        if (usuarioOptional.isEmpty()) {
            // Dica: Adicione uma mensagem de erro ao FacesContext para o usuário saber que falhou
            return null;
        }

        Usuario usuario = usuarioOptional.get();
        boolean senhaCorreta = BCrypt.checkpw(senha, usuario.getSenha());

        if (!senhaCorreta) {
            return null;
        }

        if (!usuario.getEmailVerificado()) {
            return Paginas.CONFIRMAR_EMAIL + "?faces-redirect=true";
        }

        // --- AQUI ESTÁ O QUE FALTAVA ---
        this.usuarioLogado = usuario;
        // -------------------------------

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("usuario", usuario);

        return Paginas.DASHBOARD + "?faces-redirect=true";
    }

    public String sair() {

        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .invalidateSession();

        return Paginas.LOGIN + "?faces-redirect=true";
    }

    public boolean logado() {
        return usuarioLogado != null;
    }

    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setUsuarioLogado (Usuario u) { usuarioLogado = u; }

    public String getSenha() { return senha; }
    public String getEmail() { return email; }
    public Usuario getUsuarioLogado() { return usuarioLogado; }
}