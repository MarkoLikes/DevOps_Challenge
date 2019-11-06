package com.mx.mestudillo.modelo;

import com.mx.mestudillo.dao.Mensaje;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Marco Estudillo
 */
public class ConexionMail {

    /**
     * Variables encargadas de la gestión de descarga de mensajes desde el
     * servidor de GMAIL
     */
    private Store store;
    private final String host;
    private int total;
    private int devops;

    /**
     * Se inicializa el host de GMAIL para la descarga de correos vía POP3
     */
    public ConexionMail() {
        this.host = "pop.gmail.com";
    }

    /**
     * Función encargada de establecer la conexión con el servidor POP3 de GMAIL
     *
     * @param usuario
     * @param password
     * @return String : Estatus de conexión
     */
    public String conectar(String usuario, String password) {
        Properties properties = new Properties();
        properties.put("mail.pop3.host", this.host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        Session emailSession = Session.getDefaultInstance(properties);
        try {
            store = emailSession.getStore("imaps");
            store.connect(this.host, usuario, password);
            return "OK";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Función encargada de recuperar los mensajes del servidor
     *
     * @return Lista de Mensajes
     */
    public List<Mensaje> recuperarMensajes() {
        List<Mensaje> mensajes = new ArrayList<>();
        Folder emailFolder;

        try {
            // Se recuperan únicamente los mensajes de la bandeja de entrada
            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message[] messages = emailFolder.getMessages();

            // Se contabiliza el No. total de Mensajes
            this.total = messages.length;
            String asunto, fecha, cuerpo, remitente;

            // Se leen todos los mensajes y se identifican los que incluyan "DevOps"
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                asunto = message.getSubject();
                fecha = message.getSentDate().toString();
                remitente = message.getFrom()[0].toString();

                // Se determina el cuerpo del mensaje, dependiendo si es texto plano ó MIME
                if (message.isMimeType("text/plain")) {
                    cuerpo = message.getContent().toString();
                } else {
                    MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                    cuerpo = getTextFromMimeMultipart(mimeMultipart);
                }

                // Busca la palabra DEVOPS en el asunto ó cuerpo del email
                if (asunto.toUpperCase().contains("DEVOPS") || cuerpo.toUpperCase().contains("DEVOPS")) {
                    Mensaje mensaje = new Mensaje();
                    mensaje.setNoMensaje(i);
                    mensaje.setRemitente(remitente);
                    mensaje.setAsunto(asunto);
                    mensaje.setFecha(fecha);
                    mensaje.setCuerpo(cuerpo);
                    mensajes.add(mensaje);

                    this.devops = this.devops + 1;
                }
            }

            emailFolder.close(false);
            store.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return mensajes;
    }

    /**
     * Función encargada de obtener el cuerpo de un mensaje MIME
     *
     * @return Mensaje
     */
    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) {
        String result = "";
        int count;
        try {
            count = mimeMultipart.getCount();

            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    break;
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
                } else if (bodyPart.getContent() instanceof MimeMultipart) {
                    result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public int getTotal() {
        return total;
    }

    public int getDevops() {
        return devops;
    }
}
