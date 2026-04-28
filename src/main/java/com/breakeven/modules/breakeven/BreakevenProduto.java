package com.breakeven.modules.breakeven;

import java.math.BigDecimal;

public class BreakevenProduto {
    private String nomeProduto;
    private BigDecimal custoUnitario;
    private BigDecimal totalConsumiveis;
    private BigDecimal custoFixo1;
    private BigDecimal percentagem;
    private BigDecimal valorInvestimentoPorUnidade;
    private BigDecimal transporte;
    private BigDecimal breakEven;
    private Integer quantidade;

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public BigDecimal getCustoUnitario() { return custoUnitario; }
    public void setCustoUnitario(BigDecimal custoUnitario) { this.custoUnitario = custoUnitario; }
    public BigDecimal getTotalConsumiveis() { return totalConsumiveis; }
    public void setTotalConsumiveis(BigDecimal totalConsumiveis) { this.totalConsumiveis = totalConsumiveis; }
    public BigDecimal getCustoFixo1() { return custoFixo1; }
    public void setCustoFixo1(BigDecimal custoFixo1) { this.custoFixo1 = custoFixo1; }
    public BigDecimal getPercentagem() { return percentagem; }
    public void setPercentagem(BigDecimal percentagem) { this.percentagem = percentagem; }
    public BigDecimal getValorInvestimentoPorUnidade() { return valorInvestimentoPorUnidade; }
    public void setValorInvestimentoPorUnidade(BigDecimal valorInvestimentoPorUnidade) { this.valorInvestimentoPorUnidade = valorInvestimentoPorUnidade; }
    public BigDecimal getTransporte() { return transporte; }
    public void setTransporte(BigDecimal transporte) { this.transporte = transporte; }
    public BigDecimal getBreakEven() { return breakEven; }
    public void setBreakEven(BigDecimal breakEven) { this.breakEven = breakEven; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}