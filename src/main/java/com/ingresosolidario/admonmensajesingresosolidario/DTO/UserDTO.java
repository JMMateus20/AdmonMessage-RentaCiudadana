package com.ingresosolidario.admonmensajesingresosolidario.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

    private String ldapAsesor;
    private String memberOf;
    @JsonProperty("Nombre")
    private String nombre;
    @JsonProperty("ResponseStatus")
    private String responseStatus;
    @JsonProperty("IdentificacionAsesor")
    private String identificacionAsesor;
    @JsonProperty("Apellido")
    private String apellido;
    @JsonProperty("Vicepresidencia")
    private String vicepresidencia;
    @JsonProperty("Gerencia")
    private String gerencia;
    @JsonProperty("Cargo")
    private String cargo;
    @JsonProperty("Mail")
    private String mail;
    @JsonProperty("Ciudad")
    private String ciudad;
    @JsonProperty("Edificio")
    private String edificio;
    @JsonProperty("Piso")
    private String piso;
    @JsonProperty("Response")
    private String response;
    @JsonProperty("Message")
    private String message;

    public UserDTO(String ldapAsesor, String memberOf, String nombre, String identificacionAsesor, String apellido, String cargo, String vicepresidencia, String gerencia, String mail, String ciudad, String edificio, String piso, String response, String message) {
        this.ldapAsesor = ldapAsesor;
        this.memberOf = memberOf;
        this.nombre = nombre;
        this.identificacionAsesor = identificacionAsesor;
        this.apellido = apellido;
        this.cargo = cargo;
        this.vicepresidencia = vicepresidencia;
        this.gerencia = gerencia;
        this.mail = mail;
        this.ciudad = ciudad;
        this.edificio = edificio;
        this.piso = piso;
        this.response = response;
        this.message = message;
    }

    public UserDTO() {
    }

    public String getLdapAsesor() {
        return ldapAsesor;
    }

    public void setLdapAsesor(String ldapAsesor) {
        this.ldapAsesor = ldapAsesor;
    }

    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacionAsesor() {
        return identificacionAsesor;
    }

    public void setIdentificacionAsesor(String identificacionAsesor) {
        this.identificacionAsesor = identificacionAsesor;
    }

    public String getVicepresidencia() {
        return vicepresidencia;
    }

    public void setVicepresidencia(String vicepresidencia) {
        this.vicepresidencia = vicepresidencia;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGerencia() {
        return gerencia;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
