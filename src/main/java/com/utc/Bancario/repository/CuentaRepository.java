package com.utc.Bancario.repository;

import com.utc.Bancario.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    // Buscar cuenta por número para validar duplicados
    java.util.Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}