package com.cacambeiro.cacambeiro_api.controller;

import com.cacambeiro.cacambeiro_api.model.Cacamba;
import com.cacambeiro.cacambeiro_api.projection.CacambaDetalhe; // Importar a projeção
import com.cacambeiro.cacambeiro_api.service.CacambaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cacambas")
public class CacambaController {

    @Autowired
    private CacambaService cacambaService;

    @GetMapping
    public List<Cacamba> getAllCacambas() {
        return cacambaService.findAllCacambas();
    }

    @GetMapping("/detalhes") 
    public List<CacambaDetalhe> getAllCacambasDetalhes() {
        return cacambaService.findAllCacambasDetalhes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cacamba> getCacambaById(@PathVariable Long id) {
        return cacambaService.findCacambaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cacamba> createCacamba(@RequestBody Cacamba cacamba) {
        Cacamba savedCacamba = cacambaService.saveCacamba(cacamba);
        return new ResponseEntity<>(savedCacamba, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cacamba> updateCacamba(@PathVariable Long id, @RequestBody Cacamba cacamba) {
        Cacamba updatedCacamba = cacambaService.updateCacamba(id, cacamba);
        return updatedCacamba != null ? ResponseEntity.ok(updatedCacamba) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCacamba(@PathVariable Long id) {
        cacambaService.deleteCacamba(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alugadas")
    public List<Cacamba> getCacambasAlugadas() {
        return cacambaService.findCacambasAlugadas();
    }

    @GetMapping("/disponiveis")
    public List<Cacamba> getCacambasDisponiveis() {
        return cacambaService.findCacambasDisponiveis();
    }

    @GetMapping("/cacambeiro/{cacambeiroId}")
    public List<Cacamba> getCacambasByCacambeiro(@PathVariable Long cacambeiroId) {
        return cacambaService.findCacambasByCacambeiro(cacambeiroId);
    }

    @PutMapping("/{cacambaId}/associar/{cacambeiroId}")
    public ResponseEntity<Cacamba> associarCacambaACacambeiro(@PathVariable Long cacambaId, @PathVariable Long cacambeiroId) {
        Cacamba updatedCacamba = cacambaService.associarCacambaACacambeiro(cacambaId, cacambeiroId);
        return updatedCacamba != null ? ResponseEntity.ok(updatedCacamba) : ResponseEntity.notFound().build();
    }
}