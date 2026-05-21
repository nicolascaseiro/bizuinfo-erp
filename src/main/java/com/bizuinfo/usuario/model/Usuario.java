package com.bizuinfo.usuario.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "email_verificado", nullable = false)
    private boolean emailVerificado = false;

    @Column(name = "token_verificacao")
    private String tokenVerificacao;

    @Column(name = "token_verificacao_expiracao")
    private LocalDateTime tokenVerificacaoExpiracao;

    // Recuperação de acesso
    @Column(name = "token_recuperacao")
    private String tokenRecuperacao;

    @Column(name = "token_recuperacao_expiracao")
    private LocalDateTime tokenRecuperacaoExpiracao;

    // Controle de reenvio
    @Column(name = "token_ultimo_envio")
    private LocalDateTime ultimoEnvioToken;

    // Construtor JPA
    public Usuario() {}

    // Construtor cadastro
    public Usuario(String nome, String email, String senha) {

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.emailVerificado = false;
        this.roles.add(Role.FUNCIONARIO);

    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public Set<Role> getRoles() { return roles; }
    public boolean getEmailVerificado() { return emailVerificado; }
    public String getTokenVerificacao() { return tokenVerificacao; }
    public LocalDateTime getTokenVerificacaoExpiracao() { return tokenVerificacaoExpiracao; }
    public String getTokenRecuperacao() { return tokenRecuperacao; }
    public LocalDateTime getTokenRecuperacaoExpiracao() { return tokenRecuperacaoExpiracao; }
    public LocalDateTime getUltimoEnvioToken() { return ultimoEnvioToken; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public void setEmailVerificado(boolean emailVerificado) { this.emailVerificado = emailVerificado; }
    public void setTokenVerificacao(String tokenVerificacao) { this.tokenVerificacao = tokenVerificacao; }
    public void setTokenVerificacaoExpiracao(LocalDateTime expiracao) { this.tokenVerificacaoExpiracao = expiracao; }
    public void setTokenRecuperacao(String tokenRecuperacao) { this.tokenRecuperacao = tokenRecuperacao; }
    public void setTokenRecuperacaoExpiracao(LocalDateTime expiracao) { this.tokenRecuperacaoExpiracao = expiracao; }
    public void setUltimoEnvioToken(LocalDateTime t) { this.ultimoEnvioToken = t; }
}