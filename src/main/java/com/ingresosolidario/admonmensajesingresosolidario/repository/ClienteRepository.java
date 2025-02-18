package com.ingresosolidario.admonmensajesingresosolidario.repository;

import com.ingresosolidario.admonmensajesingresosolidario.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
