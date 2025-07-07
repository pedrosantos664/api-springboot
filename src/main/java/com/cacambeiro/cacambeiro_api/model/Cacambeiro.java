package com.cacambeiro.cacambeiro_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importar JsonIgnore

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cacambeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String contato; 

    @OneToMany(mappedBy = "cacambeiro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private List<Cacamba> cacambas;
}