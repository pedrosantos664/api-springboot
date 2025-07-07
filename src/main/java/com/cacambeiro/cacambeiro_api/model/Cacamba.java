package com.cacambeiro.cacambeiro_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cacamba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoIdentificacao;
    private double capacidadeArmazenamento;
    private LocalDate dataInicioAluguel;
    private LocalDate dataTerminoAluguel;
    private boolean alugada;

    @ManyToOne
    @JoinColumn(name = "cacambeiro_id")
    private Cacambeiro cacambeiro; 
    
}