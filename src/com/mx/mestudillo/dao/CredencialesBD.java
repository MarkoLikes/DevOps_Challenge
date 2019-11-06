package com.mx.mestudillo.dao;

/**
 *
 * @author Marco Estudillo
 */
public class CredencialesBD {

    /**
     * Credenciales para la conexión con MySQL
     */
    private static final String USUARIO = "root";
    private static final String PASSWORD = "RootMysql1234";

    public String getUsuario() {
        return CredencialesBD.USUARIO;
    }

    public String getPassword() {
        return CredencialesBD.PASSWORD;
    }
}
