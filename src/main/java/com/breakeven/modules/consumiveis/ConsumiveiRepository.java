package com.breakeven.modules.consumiveis;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsumiveiRepository extends JpaRepository<Consumivel, Long> {
    List<Consumivel> findByTenantId(Long tenantId);

    @Query("SELECT COALESCE(SUM(c.custoUnitario), 0) FROM Consumivel c WHERE c.tenant.id = :tenantId")
    BigDecimal calcularTotalConsumiveis(@Param("tenantId") Long tenantId);
}