package com.breakeven.modules.lotes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    List<Lote> findByTenantIdOrderByDataCompraAsc(Long tenantId);
}