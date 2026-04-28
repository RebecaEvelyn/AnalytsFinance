package com.breakeven.modules.investimento;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustoInvestimentoRepository extends JpaRepository<CustoInvestimento, Long> {

    List<CustoInvestimento> findByTenantIdAndMesAndAnoOrderByNomeAsc(Long tenantId, Integer mes, Integer ano);

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM CustoInvestimento c WHERE c.tenant.id = :tenantId AND c.mes = :mes AND c.ano = :ano")
    BigDecimal totalPorMes(@Param("tenantId") Long tenantId, @Param("mes") Integer mes, @Param("ano") Integer ano);
}