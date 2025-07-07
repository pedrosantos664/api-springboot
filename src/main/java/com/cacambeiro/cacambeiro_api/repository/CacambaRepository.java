package com.cacambeiro.cacambeiro_api.repository;

import com.cacambeiro.cacambeiro_api.model.Cacamba;
import com.cacambeiro.cacambeiro_api.projection.CacambaDetalhe; // Importar a projeção
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacambaRepository extends JpaRepository<Cacamba, Long> {
    List<Cacamba> findByAlugadaTrue();
    List<Cacamba> findByAlugadaFalse();
    List<Cacamba> findByCacambeiroId(Long cacambeiroId);

 
    List<CacambaDetalhe> findAllBy();
}