package com.breakeven.modules.consumiveis;

import com.breakeven.modules.tenant.Tenant;
import com.breakeven.modules.tenant.TenantRepository;
import com.breakeven.security.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConsumiveiService {

    private final ConsumiveiRepository consumiveiRepository;
    private final TenantRepository tenantRepository;

    public ConsumiveiService(ConsumiveiRepository consumiveiRepository,
                              TenantRepository tenantRepository) {
        this.consumiveiRepository = consumiveiRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public Consumivel criar(ConsumiveiRequest request) {
        Long tenantId = TenantContext.getTenantId();
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));

        Consumivel consumivel = new Consumivel();
        consumivel.setNome(request.getNome());
        consumivel.setCustoTotal(request.getCustoTotal());
        consumivel.setQuantidade(request.getQuantidade());
        consumivel.setTenant(tenant);
        return consumiveiRepository.save(consumivel);
    }

    @Transactional
    public Consumivel editar(Long id, ConsumiveiRequest request) {
        Long tenantId = TenantContext.getTenantId();
        Consumivel consumivel = consumiveiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumível não encontrado"));

        if (!consumivel.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Acesso negado");
        }

        consumivel.setNome(request.getNome());
        consumivel.setCustoTotal(request.getCustoTotal());
        consumivel.setQuantidade(request.getQuantidade());
        return consumiveiRepository.save(consumivel);
    }

    @Transactional
    public void eliminar(Long id) {
        Long tenantId = TenantContext.getTenantId();
        Consumivel consumivel = consumiveiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumível não encontrado"));

        if (!consumivel.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Acesso negado");
        }

        consumiveiRepository.delete(consumivel);
    }

    public List<Consumivel> listar() {
        Long tenantId = TenantContext.getTenantId();
        return consumiveiRepository.findByTenantId(tenantId);
    }

    public BigDecimal totalConsumiveis() {
        Long tenantId = TenantContext.getTenantId();
        return consumiveiRepository.calcularTotalConsumiveis(tenantId);
    }
}