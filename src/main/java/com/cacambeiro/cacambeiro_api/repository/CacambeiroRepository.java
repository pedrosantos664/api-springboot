package com.cacambeiro.cacambeiro_api.repository;

import com.cacambeiro.cacambeiro_api.model.Cacambeiro;
import com.cacambeiro.cacambeiro_api.projection.CacambeiroInfo; // Importar a projeção
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacambeiroRepository extends JpaRepository<Cacambeiro, Long> {
    // Novo método para buscar a projeção
    List<CacambeiroInfo> findAllBy();
}