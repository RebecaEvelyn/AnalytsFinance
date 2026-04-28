package com.breakeven.modules.lotes;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breakeven.modules.tenant.Tenant;
import com.breakeven.modules.tenant.TenantRepository;
import com.breakeven.security.TenantContext;

@Service
public class LoteService {

    private final LoteRepository loteRepository;
    private final LoteProdutoRepository loteProdutoRepository;
    private final TenantRepository tenantRepository;

    public LoteService(LoteRepository loteRepository,
                       LoteProdutoRepository loteProdutoRepository,
                       TenantRepository tenantRepository) {
        this.loteRepository = loteRepository;
        this.loteProdutoRepository = loteProdutoRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public Lote criarLote(LoteRequest request) {
        Long tenantId = TenantContext.getTenantId();
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));

        Lote lote = new Lote();
        lote.setNome(request.getNome());
        lote.setDataCompra(request.getDataCompra());
        lote.setDescricao(request.getDescricao());
        lote.setTenant(tenant);
        loteRepository.save(lote);

        for (LoteRequest.ProdutoRequest p : request.getProdutos()) {
            LoteProduto produto = new LoteProduto();
            produto.setLote(lote);
            produto.setNomeProduto(p.getNomeProduto());
            produto.setQuantidadeComprada(p.getQuantidadeComprada());
            produto.setQuantidadeRestante(p.getQuantidadeComprada());
            produto.setCustoTotal(p.getCustoTotal());
            produto.setTransporte(p.getTransporte() != null ? p.getTransporte() : BigDecimal.ZERO);
            produto.setConsumiveis(p.getConsumiveis() != null ? p.getConsumiveis() : BigDecimal.ZERO);
            produto.setTenant(tenant);
            loteProdutoRepository.save(produto);
        }

        return lote;
    }

    public List<Lote> listarLotes() {
        Long tenantId = TenantContext.getTenantId();
        return loteRepository.findByTenantIdOrderByDataCompraAsc(tenantId);
    }

    public List<LoteProduto> listarProdutosDoLote(Long loteId) {
        Long tenantId = TenantContext.getTenantId();
        return loteProdutoRepository.findByLoteIdAndTenantId(loteId, tenantId);
    }

    public List<LoteProduto> listarProdutosAtivos() {
        Long tenantId = TenantContext.getTenantId();
        return loteProdutoRepository.findByTenantIdAndEstado(tenantId, EstadoLote.ATIVO);
    }

    @Transactional
    public LoteProduto esgotarManualmente(Long loteProdutoId) {
        Long tenantId = TenantContext.getTenantId();
        LoteProduto produto = loteProdutoRepository.findById(loteProdutoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!produto.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Acesso negado");
        }

        produto.setEstado(EstadoLote.ESGOTADO);
        produto.setEsgotadoManualmente(true);
        produto.setQuantidadeRestante(0);
        return loteProdutoRepository.save(produto);
    }

    @Transactional
    public LoteProduto descontarStock(String nomeProduto, Integer quantidade) {
        Long tenantId = TenantContext.getTenantId();

        LoteProduto produto = loteProdutoRepository
                .findLoteActivoMaisAntigo(tenantId, nomeProduto)
                .orElseThrow(() -> new RuntimeException("Nenhum lote activo para: " + nomeProduto));

        int novaQuantidade = produto.getQuantidadeRestante() - quantidade;

        if (novaQuantidade <= 0) {
            produto.setQuantidadeRestante(0);
            produto.setEstado(EstadoLote.ESGOTADO);
        } else {
            produto.setQuantidadeRestante(novaQuantidade);
        }

        return loteProdutoRepository.save(produto);
    }

    @Transactional
    public LoteProduto actualizarTransporte(Long id, BigDecimal transporte) {
        Long tenantId = TenantContext.getTenantId();
        LoteProduto produto = loteProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!produto.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Acesso negado");
        }

        produto.setTransporte(transporte);
        return loteProdutoRepository.save(produto);
    }

    @Transactional
    public LoteProduto actualizarConsumiveis(Long id, BigDecimal consumiveis) {
        Long tenantId = TenantContext.getTenantId();
        LoteProduto produto = loteProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!produto.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Acesso negado");
        }

        produto.setConsumiveis(consumiveis);
        return loteProdutoRepository.save(produto);
    }
}