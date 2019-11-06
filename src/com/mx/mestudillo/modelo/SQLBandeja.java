package com.mx.mestudillo.modelo;

import com.mx.mestudillo.dao.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marco Estudillo
 */
public class SQLBandeja {

    /**
     * Variables que permitirán realizar la conexión, consultas y
     * actualizaciones en la Base de Datos
     */
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private String sql;
    private int tuples;

    private final ConexionBD conexion;

    /**
     * Se inicializan las credenciales de conexión
     */
    public SQLBandeja() {
        this.conexion = new ConexionBD();
    }

    /**
     * Función que devuelve el último mensaje que se guardó del usuario en la
     * Base de Datos
     *
     * @param correo
     * @return noMensaje
     */
    public int getNoMensaje(String correo) {

        // Sentencia SQL para la consulta del último mensaje del usuario
        sql = "SELECT noMensaje AS id FROM mensaje WHERE usuario = ? "
                + "ORDER BY noMensaje DESC LIMIT 1";
        connection = this.conexion.conectar();
        int noMensaje = 0;

        // Envío de la sentencia y los datos al manejador de MySQL
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, correo);
            result = statement.executeQuery();

            if (result.next()) {
                noMensaje = result.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        this.conexion.desconectar(connection);
        return noMensaje;
    }

    /**
     * Método para registrar nuevos mensajes en la Base de Datos
     *
     * @param correo
     * @param mensaje
     */
    public void guardarMensaje(String correo, Mensaje mensaje) {

        // Sentencia SQL para el registro de un nuevo mensaje
        sql = "INSERT INTO mensaje(noMensaje, usuario, fecha, remitente, subject) "
                + "VALUES(?, ?, ?, ?, ?)";

        connection = this.conexion.conectar();

        // Envío de la sentencia y los datos al manejador de MySQL
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, mensaje.getNoMensaje());
            statement.setString(2, correo);
            statement.setString(3, mensaje.getFecha());
            statement.setString(4, mensaje.getRemitente());
            statement.setString(5, mensaje.getAsunto());

            tuples = statement.executeUpdate();

            if (tuples == 1) {
                System.out.println("\nNuevo mensaje agregado con éxito a la Base de Datos...");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        this.conexion.desconectar(connection);
    }
}
