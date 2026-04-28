package com.breakeven.modules.lotes;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*controlador dos produtos em lote se está esgotado ou nao */
@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    private final LoteService loteService;

    public LoteController(LoteService loteService) {
        this.loteService = loteService;
    }

    @PostMapping
    public ResponseEntity<Lote> criar(@RequestBody LoteRequest request) {
        return ResponseEntity.ok(loteService.criarLote(request));
    }

    @GetMapping
    public ResponseEntity<List<Lote>> listar() {
        return ResponseEntity.ok(loteService.listarLotes());
    }

    @GetMapping("/{loteId}/produtos")
    public ResponseEntity<List<LoteProduto>> listarProdutos(@PathVariable Long loteId) {
        return ResponseEntity.ok(loteService.listarProdutosDoLote(loteId));
    }

    @GetMapping("/produtos/ativos")
    public ResponseEntity<List<LoteProduto>> listarAtivos() {
        return ResponseEntity.ok(loteService.listarProdutosAtivos());
    }

    @PatchMapping("/produtos/{id}/esgotar")
    public ResponseEntity<LoteProduto> esgotar(@PathVariable Long id) {
        return ResponseEntity.ok(loteService.esgotarManualmente(id));
    }
}