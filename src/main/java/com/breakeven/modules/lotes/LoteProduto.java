package com.breakeven.modules.lotes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.breakeven.modules.tenant.Tenant;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "lote_produtos")
public class LoteProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "quantidade_comprada", nullable = false)
    private Integer quantidadeComprada;

    @Column(name = "custo_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoTotal;

    @Column(name = "custo_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoUnitario;

    @Column(name = "quantidade_restante", nullable = false)
    private Integer quantidadeRestante;

    @Column(name = "transporte", nullable = false, precision = 10, scale = 2)
    private BigDecimal transporte = BigDecimal.ZERO;

    @Column(name = "consumiveis", nullable = false, precision = 10, scale = 2)
    private BigDecimal consumiveis = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLote estado = EstadoLote.ATIVO;

    @Column(name = "esgotado_manualmente", nullable = false)
    private Boolean esgotadoManualmente = false;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    public void calcularCustoUnitario() {
        if (custoTotal != null && quantidadeComprada != null && quantidadeComprada > 0) {
            custoUnitario = custoTotal.divide(
                BigDecimal.valueOf(quantidadeComprada),
                2,
                java.math.RoundingMode.HALF_UP
            );
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public Integer getQuantidadeComprada() { return quantidadeComprada; }
    public void setQuantidadeComprada(Integer quantidadeComprada) { this.quantidadeComprada = quantidadeComprada; }
    public BigDecimal getCustoTotal() { return custoTotal; }
    public void setCustoTotal(BigDecimal custoTotal) { this.custoTotal = custoTotal; }
    public BigDecimal getCustoUnitario() { return custoUnitario; }
    public void setCustoUnitario(BigDecimal custoUnitario) { this.custoUnitario = custoUnitario; }
    public Integer getQuantidadeRestante() { return quantidadeRestante; }
    public void setQuantidadeRestante(Integer quantidadeRestante) { this.quantidadeRestante = quantidadeRestante; }
    public BigDecimal getTransporte() { return transporte; }
    public void setTransporte(BigDecimal transporte) { this.transporte = transporte; }
    public BigDecimal getConsumiveis() { return consumiveis; }
    public void setConsumiveis(BigDecimal consumiveis) { this.consumiveis = consumiveis; }
    public EstadoLote getEstado() { return estado; }
    public void setEstado(EstadoLote estado) { this.estado = estado; }
    public Boolean getEsgotadoManualmente() { return esgotadoManualmente; }
    public void setEsgotadoManualmente(Boolean esgotadoManualmente) { this.esgotadoManualmente = esgotadoManualmente; }
    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}