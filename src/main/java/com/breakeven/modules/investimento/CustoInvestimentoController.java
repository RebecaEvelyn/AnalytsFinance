package com.breakeven.modules.investimento;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investimento")
public class CustoInvestimentoController {

    private final CustoInvestimentoService service;

    public CustoInvestimentoController(CustoInvestimentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustoInvestimento> criar(@RequestBody CustoInvestimentoRequest request) {
        return ResponseEntity.ok(service.criar(request));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<CustoInvestimento>> criarLote(@RequestBody List<CustoInvestimentoRequest> requests) {
        return ResponseEntity.ok(service.criarLote(requests));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustoInvestimento> editar(@PathVariable Long id, @RequestBody CustoInvestimentoRequest request) {
        return ResponseEntity.ok(service.editar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustoInvestimento>> listar(@RequestParam Integer mes, @RequestParam Integer ano) {
        return ResponseEntity.ok(service.listarPorMes(mes, ano));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> total(@RequestParam Integer mes, @RequestParam Integer ano) {
        return ResponseEntity.ok(service.totalPorMes(mes, ano));
    }
}