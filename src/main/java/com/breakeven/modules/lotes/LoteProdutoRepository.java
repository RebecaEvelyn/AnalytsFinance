package com.breakeven.modules.lotes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoteProdutoRepository extends JpaRepository<LoteProduto, Long> {

    List<LoteProduto> findByTenantIdAndEstado(Long tenantId, EstadoLote estado);

    // Busca o lote activo mais antigo de um produto (FIFO)
    @Query("SELECT lp FROM LoteProduto lp WHERE lp.tenant.id = :tenantId " +
           "AND lp.nomeProduto = :nomeProduto AND lp.estado = 'ATIVO' " +
           "ORDER BY lp.lote.dataCompra ASC")
    Optional<LoteProduto> findLoteActivoMaisAntigo(
        @Param("tenantId") Long tenantId,
        @Param("nomeProduto") String nomeProduto
    );

    List<LoteProduto> findByLoteIdAndTenantId(Long loteId, Long tenantId);
}