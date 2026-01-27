package com.utc.Bancario.service;

import com.utc.Bancario.entity.Cuenta;
import com.utc.Bancario.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional(readOnly = true)
    public List<Cuenta> listarTodas() { return cuentaRepository.findAll(); }

    @Transactional
    public void guardar(Cuenta cuenta) { cuentaRepository.save(cuenta); }

    @Transactional(readOnly = true)
    public Cuenta obtenerPorId(Long id) { return cuentaRepository.findById(id).orElse(null); }

    @Transactional
    public void eliminar(Long id) { cuentaRepository.deleteById(id); }

    // Método para validar unicidad de número de cuenta
    @Transactional(readOnly = true)
    public java.util.Optional<Cuenta> obtenerPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }
}