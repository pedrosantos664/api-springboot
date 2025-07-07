package com.cacambeiro.cacambeiro_api.service;

import com.cacambeiro.cacambeiro_api.model.Usuario;
import com.cacambeiro.cacambeiro_api.repository.UsuarioRepository;
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
@CacheConfig(cacheNames = "usuarios") 
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Cacheable
    public List<Usuario> findAllUsuarios() {
        System.out.println("Buscando todos os usuários do banco de dados...");
        return usuarioRepository.findAll();
    }

    @Cacheable(key = "#id")
    public Optional<Usuario> findUsuarioById(Long id) {
        System.out.println("Buscando usuário por ID " + id + " do banco de dados...");
        return usuarioRepository.findById(id);
    }

    @CachePut(key = "#usuario.id")
    @CacheEvict(allEntries = true, condition = "#usuario.id == null")
    public Usuario saveUsuario(Usuario usuario) {
        System.out.println("Salvando/Atualizando usuário no banco de dados...");
        return usuarioRepository.save(usuario);
    }

    @Caching(evict = {
        @CacheEvict(key = "#id"), 
        @CacheEvict(allEntries = true) 
    })
    public void deleteUsuario(Long id) {
        System.out.println("Deletando usuário por ID " + id + " do banco de dados...");
        usuarioRepository.deleteById(id);
    }

    @CachePut(key = "#id")
    @CacheEvict(allEntries = true)
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        System.out.println("Atualizando usuário por ID " + id + " no banco de dados...");
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario existingUsuario = usuario.get();
            existingUsuario.setUsername(usuarioDetails.getUsername());
            existingUsuario.setEmail(usuarioDetails.getEmail());
        
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    @Cacheable(key = "#username")
    public Usuario findByUsername(String username) {
        System.out.println("Buscando usuário por username " + username + " do banco de dados...");
        return usuarioRepository.findByUsername(username);
    }
}