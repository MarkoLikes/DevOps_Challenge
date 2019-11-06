package com.mx.mestudillo.dao;

/**
 *
 * @author Marco Estudillo
 */
public class Mensaje {

    /**
     * Variables que permiten registrar la información de los mensajes en la BD
     */
    private int noMensaje;
    private String remitente;
    private String asunto;
    private String fecha;
    private String cuerpo;
    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getNoMensaje() {
        return noMensaje;
    }

    public void setNoMensaje(int noMensaje) {
        this.noMensaje = noMensaje;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
