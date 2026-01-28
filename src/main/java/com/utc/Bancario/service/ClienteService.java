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


    // Validar duplicados por cédula
    @Transactional(readOnly = true)
    public boolean existeCedula(String cedula, Long id) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findByCedula(cedula);
        if (clienteEncontrado.isPresent()) {
            if (id == null) return true;
            if (!clienteEncontrado.get().getId().equals(id)) return true;
        }
        return false;
    }
}