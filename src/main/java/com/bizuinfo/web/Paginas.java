package com.bizuinfo.web;

public class Paginas {

    // Prefixos de acesso
    private static final String RESTRITO = "/restrito/";
    private static final String PUBLICO = "/publico/";

    // Role
    private static final String FUNCIONARIO = "funcionario/";
    private static final String GERENTE = "gerente/";
    private static final String ADMIN = "admin/";

    // Módulo
    private static final String ACESSO = "acesso/";
    private static final String APP = "app/";

    // Páginas públicas
    public static final String LOGIN = PUBLICO + ACESSO + "login.xhtml";
    public static final String CADASTRO = PUBLICO + ACESSO + "cadastro.xhtml";
    public static final String RECUPERAR_ACESSO = PUBLICO + ACESSO + "recuperar_acesso.xhtml";
    public static final String CONFIRMAR_EMAIL = PUBLICO + ACESSO + "confirmar_email.xhtml";
    public static final String CONSUMIR_TOKEN = PUBLICO + ACESSO + "consumir_token.xhtml";

    // Páginas restritas
    public static final String DASHBOARD = RESTRITO + APP + FUNCIONARIO + "dashboard.xhtml";
    public static final String DASHBOARD_GERENTE = RESTRITO + APP + GERENTE +  "dashboard_gerente.xhtml";
    public static final String DASHBOARD_ADMIN = RESTRITO + APP + ADMIN + "dashboard_admin.xhtml";

    // Outras
    public static final String ACESSO_NEGADO = PUBLICO + "acesso_negado.xhtml";


}
