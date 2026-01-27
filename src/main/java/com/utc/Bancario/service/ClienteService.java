package com.utc.Bancario.service;

import com.utc.Bancario.entity.Cliente;
import com.utc.Bancario.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    // Lógica para evitar duplicados, permitiendo editar el propio registro
    @Transactional(readOnly = true)
    public boolean existeEmail(String email, Long id) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findByEmail(email);
        if (clienteEncontrado.isPresent()) {
            // Si es nuevo (id null) y existe -> Duplicado
            if (id == null) return true;
            // Si es edición y el ID encontrado no es el mismo -> Duplicado
            if (!clienteEncontrado.get().getId().equals(id)) return true;
        }
        return false;
    }
}