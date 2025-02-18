package com.ingresosolidario.admonmensajesingresosolidario.models;

import jakarta.persistence.*;

@Entity
@Table(name="tblMsjIngresoSolidario")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String canal;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    public Mensaje(String canal, String mensaje) {
        this.canal = canal;
        this.mensaje = mensaje;
    }

    public Mensaje() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
