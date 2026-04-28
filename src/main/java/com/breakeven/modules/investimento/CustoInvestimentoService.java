package com.breakeven.modules.investimento;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breakeven.modules.tenant.Tenant;
import com.breakeven.modules.tenant.TenantRepository;
import com.breakeven.security.TenantContext;

@Service
public class CustoInvestimentoService {

    private final CustoInvestimentoRepository repository;
    private final TenantRepository tenantRepository;

    public CustoInvestimentoService(CustoInvestimentoRepository repository,
                                     TenantRepository tenantRepository) {
        this.repository = repository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public CustoInvestimento criar(CustoInvestimentoRequest request) {
        Long tenantId = TenantContext.getTenantId();
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));

        CustoInvestimento custo = new CustoInvestimento();
        custo.setNome(request.getNome());
        custo.setValor(request.getValor());
        custo.setMes(request.getMes());
        custo.setAno(request.getAno());
        custo.setTenant(tenant);
        return repository.save(custo);
    }

    @Transactional
    public List<CustoInvestimento> criarLote(List<CustoInvestimentoRequest> requests) {
        return requests.stream().map(this::criar).toList();
    }

    @Transactional
    public CustoInvestimento editar(Long id, CustoInvestimentoRequest request) {
        Long tenantId = TenantContext.getTenantId();
        CustoInvestimento custo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custo não encontrado"));

        if (!custo.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Acesso negado");
        }

        custo.setNome(request.getNome());
        custo.setValor(request.getValor());
        custo.setMes(request.getMes());
        custo.setAno(request.getAno());
        return repository.save(custo);
    }

    @Transactional
    public void eliminar(Long id) {
        Long tenantId = TenantContext.getTenantId();
        CustoInvestimento custo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custo não encontrado"));

        if (!custo.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Acesso negado");
        }

        repository.delete(custo);
    }

    public List<CustoInvestimento> listarPorMes(Integer mes, Integer ano) {
        Long tenantId = TenantContext.getTenantId();
        return repository.findByTenantIdAndMesAndAnoOrderByNomeAsc(tenantId, mes, ano);
    }

    public BigDecimal totalPorMes(Integer mes, Integer ano) {
        Long tenantId = TenantContext.getTenantId();
        return repository.totalPorMes(tenantId, mes, ano);
    }
}