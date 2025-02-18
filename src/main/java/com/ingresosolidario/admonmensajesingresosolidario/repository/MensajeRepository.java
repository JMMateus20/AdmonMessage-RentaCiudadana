package com.ingresosolidario.admonmensajesingresosolidario.repository;

import com.ingresosolidario.admonmensajesingresosolidario.models.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

}
