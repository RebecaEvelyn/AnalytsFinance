package com.breakeven.modules.lotes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.breakeven.modules.tenant.Tenant;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_compra", nullable = false)
    private LocalDate dataCompra;

    @Column
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @JsonManagedReference
    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoteProduto> produtos;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }
    public List<LoteProduto> getProdutos() { return produtos; }
    public void setProdutos(List<LoteProduto> produtos) { this.produtos = produtos; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}