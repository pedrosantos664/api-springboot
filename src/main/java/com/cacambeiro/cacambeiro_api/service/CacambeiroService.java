package com.cacambeiro.cacambeiro_api.service;

import com.cacambeiro.cacambeiro_api.model.Cacambeiro;
import com.cacambeiro.cacambeiro_api.projection.CacambeiroInfo; // Importar a projeção
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
@CacheConfig(cacheNames = "cacambeiros") 
public class CacambeiroService {

    @Autowired
    private CacambeiroRepository cacambeiroRepository;

    @Cacheable 
    public List<Cacambeiro> findAllCacambeiros() {
        System.out.println("Buscando todos os caçambeiros do banco de dados..."); 
        return cacambeiroRepository.findAll();
    }

    @Cacheable(key = "#id") 
    public Optional<Cacambeiro> findCacambeiroById(Long id) {
        System.out.println("Buscando caçambeiro por ID " + id + " do banco de dados...");
        return cacambeiroRepository.findById(id);
    }

    @CachePut(key = "#cacambeiro.id") 
    @CacheEvict(allEntries = true, condition = "#cacambeiro.id == null") 
    public Cacambeiro saveCacambeiro(Cacambeiro cacambeiro) {
        System.out.println("Salvando/Atualizando caçambeiro no banco de dados...");
        return cacambeiroRepository.save(cacambeiro);
    }

    @Caching(evict = { 
        @CacheEvict(key = "#id"), 
        @CacheEvict(allEntries = true) 
    })
    public void deleteCacambeiro(Long id) {
        System.out.println("Deletando caçambeiro por ID " + id + " do banco de dados...");
        cacambeiroRepository.deleteById(id);
    }

    @CachePut(key = "#id") 
    @CacheEvict(allEntries = true) 
    public Cacambeiro updateCacambeiro(Long id, Cacambeiro cacambeiroDetails) {
        System.out.println("Atualizando caçambeiro por ID " + id + " no banco de dados...");
        Optional<Cacambeiro> cacambeiro = cacambeiroRepository.findById(id);
        if (cacambeiro.isPresent()) {
            Cacambeiro existingCacambeiro = cacambeiro.get();
            existingCacambeiro.setNome(cacambeiroDetails.getNome());
            existingCacambeiro.setContato(cacambeiroDetails.getContato());
            return cacambeiroRepository.save(existingCacambeiro);
        }
        return null;
    }

    @Cacheable(cacheNames = "cacambeirosInfo") 
    public List<CacambeiroInfo> findAllCacambeirosInfo() {
        System.out.println("Buscando informações de caçambeiros (projeção) do banco de dados...");
        return cacambeiroRepository.findAllBy();
    }
}