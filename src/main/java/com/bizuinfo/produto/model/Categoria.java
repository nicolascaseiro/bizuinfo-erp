package com.bizuinfo.produto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nome")
    private String nome;

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria() {

    }
}
