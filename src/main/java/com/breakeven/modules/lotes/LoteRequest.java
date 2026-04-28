package com.breakeven.modules.lotes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
/*Controlador dos lotes */
public class LoteRequest {

    private String nome;
    private LocalDate dataCompra;
    private String descricao;
    private List<ProdutoRequest> produtos;

    public static class ProdutoRequest {
        private String nomeProduto;
        private Integer quantidadeComprada;
        private BigDecimal custoTotal;

        public String getNomeProduto() { return nomeProduto; }
        public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
        public Integer getQuantidadeComprada() { return quantidadeComprada; }
        public void setQuantidadeComprada(Integer quantidadeComprada) { this.quantidadeComprada = quantidadeComprada; }
        public BigDecimal getCustoTotal() { return custoTotal; }
        public void setCustoTotal(BigDecimal custoTotal) { this.custoTotal = custoTotal; }
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<ProdutoRequest> getProdutos() { return produtos; }
    public void setProdutos(List<ProdutoRequest> produtos) { this.produtos = produtos; }
}