package com.cacambeiro.cacambeiro_api.controller;

import com.cacambeiro.cacambeiro_api.model.Cacambeiro;
import com.cacambeiro.cacambeiro_api.projection.CacambeiroInfo; // Importar a projeção
import com.cacambeiro.cacambeiro_api.service.CacambeiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cacambeiros")
public class CacambeiroController {

    @Autowired
    private CacambeiroService cacambeiroService;

    @GetMapping
    public List<Cacambeiro> getAllCacambeiros() {
        return cacambeiroService.findAllCacambeiros();
    }

    @GetMapping("/info") 
    public List<CacambeiroInfo> getAllCacambeirosInfo() {
        return cacambeiroService.findAllCacambeirosInfo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cacambeiro> getCacambeiroById(@PathVariable Long id) {
        return cacambeiroService.findCacambeiroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cacambeiro> createCacambeiro(@RequestBody Cacambeiro cacambeiro) {
        Cacambeiro savedCacambeiro = cacambeiroService.saveCacambeiro(cacambeiro);
        return new ResponseEntity<>(savedCacambeiro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cacambeiro> updateCacambeiro(@PathVariable Long id, @RequestBody Cacambeiro cacambeiro) {
        Cacambeiro updatedCacambeiro = cacambeiroService.updateCacambeiro(id, cacambeiro);
        return updatedCacambeiro != null ? ResponseEntity.ok(updatedCacambeiro) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCacambeiro(@PathVariable Long id) {
        cacambeiroService.deleteCacambeiro(id);
        return ResponseEntity.noContent().build();
    }
}