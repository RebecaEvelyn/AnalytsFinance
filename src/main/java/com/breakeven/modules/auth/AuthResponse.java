package com.breakeven.modules.auth;

public class AuthResponse {
    private String token;
    private String nome;
    private String email;
    private Long tenantId;
    private String plano;

    public AuthResponse(String token, String nome, String email, Long tenantId, String plano) {
        this.token = token;
        this.nome = nome;
        this.email = email;
        this.tenantId = tenantId;
        this.plano = plano;
    }

    public String getToken() { return token; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Long getTenantId() { return tenantId; }
    public String getPlano() { return plano; }
}