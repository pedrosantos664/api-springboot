package com.cacambeiro.cacambeiro_api.projection;

import java.time.LocalDate;


public interface CacambaDetalhe {
	 Long getId();
    String getCodigoIdentificacao();
    double getCapacidadeArmazenamento();
    boolean isAlugada();
    LocalDate getDataInicioAluguel(); 
    LocalDate getDataTerminoAluguel(); 

    // Projeção aninhada para o nome do caçambeiro
    CacambeiroInfo getCacambeiro();

    // Interface interna para a projeção do caçambeiro 
    interface CacambeiroInfo {
        String getNome();
    }
}