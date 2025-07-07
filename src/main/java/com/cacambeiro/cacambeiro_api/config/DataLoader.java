package com.cacambeiro.cacambeiro_api.config;

import com.cacambeiro.cacambeiro_api.model.Cacamba;
import com.cacambeiro.cacambeiro_api.model.Cacambeiro;
import com.cacambeiro.cacambeiro_api.model.Usuario;
import com.cacambeiro.cacambeiro_api.repository.CacambeiroRepository;
import com.cacambeiro.cacambeiro_api.repository.CacambaRepository;
import com.cacambeiro.cacambeiro_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Autowired
    private CacambeiroRepository cacambeiroRepository;
    @Autowired
    private CacambaRepository cacambaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
           
            if (cacambeiroRepository.count() == 0) {
                
                Cacambeiro cacambeiro1 = new Cacambeiro(null, "Caçambas do Zé", "zezinho@email.com", null);
                Cacambeiro cacambeiro2 = new Cacambeiro(null, "Aluguel Express", "contato@express.com", null);

                cacambeiroRepository.saveAll(Arrays.asList(cacambeiro1, cacambeiro2));

                
                Cacamba cacamba1 = new Cacamba(null, "CB-001", 5.0, LocalDate.now(), LocalDate.now().plusDays(7), false, cacambeiro1);
                Cacamba cacamba2 = new Cacamba(null, "CB-002", 7.5, LocalDate.now().minusDays(2), LocalDate.now().plusDays(5), true, cacambeiro1);
                Cacamba cacamba3 = new Cacamba(null, "CB-003", 10.0, null, null, false, cacambeiro2);
                Cacamba cacamba4 = new Cacamba(null, "CB-004", 6.0, LocalDate.now().minusDays(1), LocalDate.now().plusDays(3), true, cacambeiro2);

                cacambaRepository.saveAll(Arrays.asList(cacamba1, cacamba2, cacamba3, cacamba4));
                cacambeiro1.setCacambas(Arrays.asList(cacamba1, cacamba2));
                cacambeiro2.setCacambas(Arrays.asList(cacamba3, cacamba4));
                cacambeiroRepository.saveAll(Arrays.asList(cacambeiro1, cacambeiro2));

                System.out.println("Dados de exemplo de Caçambeiros e Caçambas carregados!");
            } else {
                System.out.println("Caçambeiros e Caçambas já existem no banco de dados. Pulando a carga de dados de exemplo.");
            }

            // Verifica se já existem usuários antes de inserir
            if (usuarioRepository.count() == 0) {
                // Criar Usuários
                Usuario usuario1 = new Usuario(null, "admin", "admin123", "admin@email.com"); 
                Usuario usuario2 = new Usuario(null, "joao", "joao123", "joao@email.com");

                usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));
                System.out.println("Dados de exemplo de Usuários carregados!");
            } else {
                System.out.println("Usuários já existem no banco de dados. Pulando a carga de dados de exemplo.");
            }
        };
    }
}