package com.bizuinfo.produto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private double preco;

    @Column(name = "estoqueAtual")
    private int estoque;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "fornecedor_idfornecedor")
    private Long idFornecedor;

    @Column(name = "categoria_categoria_id")
    private int categoria;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
