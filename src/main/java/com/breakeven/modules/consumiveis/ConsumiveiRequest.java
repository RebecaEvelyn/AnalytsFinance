package com.breakeven.modules.consumiveis;

import java.math.BigDecimal;

public class ConsumiveiRequest {
    private String nome;
    private BigDecimal custoTotal;
    private Integer quantidade;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getCustoTotal() { return custoTotal; }
    public void setCustoTotal(BigDecimal custoTotal) { this.custoTotal = custoTotal; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}