package com.breakeven.modules.investimento;

import java.math.BigDecimal;

public class CustoInvestimentoRequest {
    private String nome;
    private BigDecimal valor;
    private Integer mes;
    private Integer ano;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
}