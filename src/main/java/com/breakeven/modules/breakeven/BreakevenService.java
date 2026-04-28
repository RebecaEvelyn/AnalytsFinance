package com.breakeven.modules.breakeven;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.breakeven.modules.investimento.CustoInvestimentoRepository;
import com.breakeven.modules.lotes.EstadoLote;
import com.breakeven.modules.lotes.LoteProduto;
import com.breakeven.modules.lotes.LoteProdutoRepository;
import com.breakeven.security.TenantContext;

@Service
public class BreakevenService {

    private final LoteProdutoRepository loteProdutoRepository;
    private final CustoInvestimentoRepository investimentoRepository;

    public BreakevenService(LoteProdutoRepository loteProdutoRepository,
                            CustoInvestimentoRepository investimentoRepository) {
        this.loteProdutoRepository = loteProdutoRepository;
        this.investimentoRepository = investimentoRepository;
    }

    public List<BreakevenProduto> calcular(Integer mes, Integer ano) {
        Long tenantId = TenantContext.getTenantId();

        // 1. Busca produtos activos
        List<LoteProduto> produtos = loteProdutoRepository
                .findByTenantIdAndEstado(tenantId, EstadoLote.ATIVO);

        // 2. Total investimento do mês
        BigDecimal totalInvestimento = investimentoRepository
                .totalPorMes(tenantId, mes, ano);

        // 3. Custo total de todos os produtos (sem consumíveis)
        BigDecimal custoTotalLote = produtos.stream()
                .map(LoteProduto::getCustoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Calcula break-even por produto
        return produtos.stream().map(produto -> {
            BreakevenProduto bp = new BreakevenProduto();
            bp.setNomeProduto(produto.getNomeProduto());
            bp.setCustoUnitario(produto.getCustoUnitario());
            bp.setTotalConsumiveis(produto.getConsumiveis());
            bp.setQuantidade(produto.getQuantidadeComprada());
            bp.setTransporte(produto.getTransporte());

            // Custo Fixo 1 = (custo unitário + consumíveis do produto) × quantidade
            BigDecimal custoFixo1 = produto.getCustoUnitario()
                    .add(produto.getConsumiveis())
                    .multiply(BigDecimal.valueOf(produto.getQuantidadeComprada()));
            bp.setCustoFixo1(custoFixo1.setScale(2, RoundingMode.HALF_UP));

            // Percentagem = custo total produto / custo total lote
            BigDecimal percentagem = BigDecimal.ZERO;
            if (custoTotalLote.compareTo(BigDecimal.ZERO) > 0) {
                percentagem = produto.getCustoTotal()
                        .divide(custoTotalLote, 6, RoundingMode.HALF_UP);
            }
            bp.setPercentagem(percentagem.setScale(4, RoundingMode.HALF_UP));

            // Valor investimento por unidade = (total investimento × percentagem) / quantidade
            BigDecimal valorInvestimentoPorUnidade = BigDecimal.ZERO;
            if (produto.getQuantidadeComprada() > 0) {
                valorInvestimentoPorUnidade = totalInvestimento
                        .multiply(percentagem)
                        .divide(BigDecimal.valueOf(produto.getQuantidadeComprada()), 2, RoundingMode.HALF_UP);
            }
            bp.setValorInvestimentoPorUnidade(valorInvestimentoPorUnidade);

            // Break-even = custo unitário + consumíveis do produto + valor investimento por unidade + transporte
            BigDecimal breakEven = produto.getCustoUnitario()
                    .add(produto.getConsumiveis())
                    .add(valorInvestimentoPorUnidade)
                    .add(produto.getTransporte());
            bp.setBreakEven(breakEven.setScale(2, RoundingMode.HALF_UP));

            return bp;
        }).toList();
    }
}