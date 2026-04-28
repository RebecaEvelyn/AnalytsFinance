package com.breakeven.modules.breakeven;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/breakeven")
public class BreakevenController {

    private final BreakevenService breakevenService;

    public BreakevenController(BreakevenService breakevenService) {
        this.breakevenService = breakevenService;
    }

    @GetMapping
    public ResponseEntity<List<BreakevenProduto>> calcular(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {
        return ResponseEntity.ok(breakevenService.calcular(mes, ano));
    }
}