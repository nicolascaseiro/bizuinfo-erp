package com.bizuinfo.venda.bean;

import com.bizuinfo.acesso.bean.SessaoBean;
import com.bizuinfo.usuario.model.Usuario;
import com.bizuinfo.usuario.model.Role;
import com.bizuinfo.venda.dao.VendaDAO;
import com.bizuinfo.venda.model.Venda;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MinhasVendasBean implements Serializable {

    @Inject
    private SessaoBean sessaoBean;

    @Inject
    private VendaDAO vendaDAO;

    private List<Venda> vendas;

    public void carregar() {

        Usuario usuario = sessaoBean.getUsuarioLogado();

        if (usuario == null) return;

        if (usuario.getRole().temPermissao(Role.GERENTE)) {
            vendas = vendaDAO.buscarTodas();
        } else {
            vendas = vendaDAO.buscarPorUsuario(usuario.getId());
        }
    }

    public List<Venda> getVendas() {
        return vendas;
    }
}