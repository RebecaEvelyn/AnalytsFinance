package com.breakeven.modules.consumiveis;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumiveis")
public class ConsumiveiController {

    private final ConsumiveiService consumiveiService;

    public ConsumiveiController(ConsumiveiService consumiveiService) {
        this.consumiveiService = consumiveiService;
    }

    @PostMapping
    public ResponseEntity<Consumivel> criar(@RequestBody ConsumiveiRequest request) {
        return ResponseEntity.ok(consumiveiService.criar(request));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<Consumivel>> criarLote(@RequestBody List<ConsumiveiRequest> requests) {
        List<Consumivel> criados = requests.stream()
                .map(consumiveiService::criar)
                .toList();
        return ResponseEntity.ok(criados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consumivel> editar(@PathVariable Long id, @RequestBody ConsumiveiRequest request) {
        return ResponseEntity.ok(consumiveiService.editar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        consumiveiService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Consumivel>> listar() {
        return ResponseEntity.ok(consumiveiService.listar());
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> total() {
        return ResponseEntity.ok(consumiveiService.totalConsumiveis());
    }
}