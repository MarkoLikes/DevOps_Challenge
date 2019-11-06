package com.mx.mestudillo.modelo;

import com.mx.mestudillo.dao.CredencialesBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Marco Estudillo
 */
public class ConexionBD {

    /**
     * Driver para la conexión con MySQL y URL de la Base de Datos
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://18.188.43.162:3306/bandeja";

    /**
     * Credenciales para enlazarse con la Base de Datos
     */
    private final CredencialesBD credenciales;

    /**
     * Se inicializa el objeto que contiene las credenciales
     */
    public ConexionBD() {
        credenciales = new CredencialesBD();
    }

    /**
     * Método encargado de abrir una conexión con la Base de Datos
     *
     * @return conexion
     */
    public Connection conectar() {
        Connection conexion = null;

        try {
            Class.forName(DRIVER);
            conexion = (Connection) DriverManager.getConnection(URL, credenciales.getUsuario(),
                    credenciales.getPassword());

            if (conexion != null) {
                System.out.println("Se ha establecido la conexión con la Base de Datos.");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se ha podido establecer la conexión con la Base de Datos: ");
            System.out.println(ex.getMessage());
        }

        return conexion;
    }

    /**
     * Método que se desconecta de la base de Datos
     *
     * @param conexion
     */
    public void desconectar(Connection conexion) {
        conexion = null;

        if (conexion == null) {
            System.out.println("Se ha terminado la conexión con la Base de Datos.");
        }
    }
}
