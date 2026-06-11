package com.bizuinfo.venda.bean;

import com.bizuinfo.acesso.bean.SessaoBean;
import com.bizuinfo.usuario.model.Usuario;
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

    @PostConstruct
    public void init() {

        Usuario usuario = sessaoBean.getUsuarioLogado();

        if (usuario != null) {
            vendas = vendaDAO.buscarPorUsuario(usuario.getId());
        }
    }

    public List<Venda> getVendas() {
        return vendas;
    }
}