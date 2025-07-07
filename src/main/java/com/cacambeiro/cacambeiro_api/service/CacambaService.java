package com.cacambeiro.cacambeiro_api.service;

import com.cacambeiro.cacambeiro_api.model.Cacamba;
import com.cacambeiro.cacambeiro_api.model.Cacambeiro;
import com.cacambeiro.cacambeiro_api.projection.CacambaDetalhe; // Importar a projeção
import com.cacambeiro.cacambeiro_api.repository.CacambaRepository;
import com.cacambeiro.cacambeiro_api.repository.CacambeiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching; // Importar @Caching

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "cacambas") // Define o nome do cache para esta classe
public class CacambaService {

    @Autowired
    private CacambaRepository cacambaRepository;

    @Autowired
    private CacambeiroRepository cacambeiroRepository;

    @Cacheable // Armazena em cache o resultado desta lista de caçambas
    public List<Cacamba> findAllCacambas() {
        System.out.println("Buscando todas as caçambas do banco de dados...");
        return cacambaRepository.findAll();
    }

    @Cacheable(key = "#id") // Armazena em cache por ID
    public Optional<Cacamba> findCacambaById(Long id) {
        System.out.println("Buscando caçamba por ID " + id + " do banco de dados...");
        return cacambaRepository.findById(id);
    }

    @CachePut(key = "#cacamba.id") // Atualiza o cache após salvar/criar
    @CacheEvict(allEntries = true, condition = "#cacamba.id == null") // Evita o cache da lista se for uma nova criação
    public Cacamba saveCacamba(Cacamba cacamba) {
        System.out.println("Salvando/Atualizando caçamba no banco de dados...");
        return cacambaRepository.save(cacamba);
    }

    @Caching(evict = { // Usar @Caching para múltiplas anotações @CacheEvict
        @CacheEvict(key = "#id"), // Remove o item do cache ao deletar por ID
        @CacheEvict(allEntries = true) // Remove todas as entradas do cache (lista)
    })
    public void deleteCacamba(Long id) {
        System.out.println("Deletando caçamba por ID " + id + " do banco de dados...");
        cacambaRepository.deleteById(id);
    }

    @CachePut(key = "#id") // Atualiza o cache da entrada específica
    @CacheEvict(allEntries = true) // Remove todas as entradas do cache (lista) para garantir consistência
    public Cacamba updateCacamba(Long id, Cacamba cacambaDetails) {
        System.out.println("Atualizando caçamba por ID " + id + " no banco de dados...");
        Optional<Cacamba> cacamba = cacambaRepository.findById(id);
        if (cacamba.isPresent()) {
            Cacamba existingCacamba = cacamba.get();
            existingCacamba.setCodigoIdentificacao(cacambaDetails.getCodigoIdentificacao());
            existingCacamba.setCapacidadeArmazenamento(cacambaDetails.getCapacidadeArmazenamento());
            existingCacamba.setDataInicioAluguel(cacambaDetails.getDataInicioAluguel());
            existingCacamba.setDataTerminoAluguel(cacambaDetails.getDataTerminoAluguel());
            existingCacamba.setAlugada(cacambaDetails.isAlugada());
            if (cacambaDetails.getCacambeiro() != null && cacambaDetails.getCacambeiro().getId() != null) {
                cacambeiroRepository.findById(cacambaDetails.getCacambeiro().getId())
                        .ifPresent(existingCacamba::setCacambeiro);
            }
            return cacambaRepository.save(existingCacamba);
        }
        return null;
    }

    @Cacheable(cacheNames = "cacambasAlugadas") // Novo cache para caçambas alugadas
    public List<Cacamba> findCacambasAlugadas() {
        System.out.println("Buscando caçambas alugadas do banco de dados...");
        return cacambaRepository.findByAlugadaTrue();
    }

    @Cacheable(cacheNames = "cacambasDisponiveis") // Novo cache para caçambas disponíveis
    public List<Cacamba> findCacambasDisponiveis() {
        System.out.println("Buscando caçambas disponíveis do banco de dados...");
        return cacambaRepository.findByAlugadaFalse();
    }

    @Cacheable(cacheNames = "cacambasByCacambeiro", key = "#cacambeiroId") // Novo cache para caçambas por caçambeiro
    public List<Cacamba> findCacambasByCacambeiro(Long cacambeiroId) {
        System.out.println("Buscando caçambas para o caçambeiro " + cacambeiroId + " do banco de dados...");
        return cacambaRepository.findByCacambeiroId(cacambeiroId);
    }

    @CachePut(key = "#cacambaId") // Atualiza o cache da caçamba após associação
    @Caching(evict = { // Usar @Caching para múltiplas anotações @CacheEvict
        @CacheEvict(cacheNames = {"cacambasAlugadas", "cacambasDisponiveis", "cacambasByCacambeiro"}, allEntries = true) // Limpa caches relacionados
    })
    public Cacamba associarCacambaACacambeiro(Long cacambaId, Long cacambeiroId) {
        System.out.println("Associando caçamba " + cacambaId + " ao caçambeiro " + cacambeiroId + "...");
        Optional<Cacamba> cacambaOptional = cacambaRepository.findById(cacambaId);
        Optional<Cacambeiro> cacambeiroOptional = cacambeiroRepository.findById(cacambeiroId);

        if (cacambaOptional.isPresent() && cacambeiroOptional.isPresent()) {
            Cacamba cacamba = cacambaOptional.get();
            Cacambeiro cacambeiro = cacambeiroOptional.get();
            cacamba.setCacambeiro(cacambeiro);
            return cacambaRepository.save(cacamba);
        }
        return null;
    }

    @Cacheable(cacheNames = "cacambasDetalhes") // Novo cache para a projeção de detalhes
    public List<CacambaDetalhe> findAllCacambasDetalhes() {
        System.out.println("Buscando detalhes de caçambas (projeção) do banco de dados...");
        return cacambaRepository.findAllBy();
    }
}