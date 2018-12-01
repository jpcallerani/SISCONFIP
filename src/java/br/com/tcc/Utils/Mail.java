/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.Utils;

import br.com.tcc.modal.TblUsuario;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Jp
 */
public class Mail {

    /**
     * Função para envio de email
     *
     * @return
     */
    public static boolean enviaEmail(TblUsuario usuario, StringBuilder msg, String tipo) {
        String subject = "";
        InternetAddress addressFrom;
        InternetAddress[] addressTo;
        try {
            boolean debug = false;

            // Set the host smtp address
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtps.bol.com.br");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");

            // create some properties and get the default Session
            Session session = Session.getInstance(props, null);
            //
            session.setDebug(debug);
            //
            Message mensagem = new MimeMessage(session);
            //
            addressFrom = new InternetAddress("sisconfip@bol.com.br", "SISCONFIP");
            mensagem.setFrom(addressFrom);
            //
            // Seta o destinatário de acordo com o processamento.
            // E o titulo do email
            //
            if (tipo.equalsIgnoreCase("cadastro")) {
                subject = "Bem-Vindo ao SISCONFIP!!!!";
                addressTo = new InternetAddress[]{new InternetAddress(usuario.getEmail())};
            } else if (tipo.equalsIgnoreCase("contato")) {
                subject = "Contato: " + usuario.getEmail();
                addressTo = new InternetAddress[]{new InternetAddress("sisconfip@bol.com.br")};
            } else if (tipo.equalsIgnoreCase("trocaSenha")) {
                subject = "contato SISCONFIP (senha)";
                addressTo = new InternetAddress[]{new InternetAddress(usuario.getEmail())};
            } else {
                subject = "Contato: " + usuario.getEmail();
                addressTo = new InternetAddress[]{new InternetAddress("sisconfip@bol.com.br")};
            }
            //
            mensagem.setSubject(subject);
            //
            mensagem.setRecipients(Message.RecipientType.TO, addressTo);
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(msg.toString(), "text/html");
            Multipart mps = new MimeMultipart();
            //adiciona o corpo texto da mensagem
            mps.addBodyPart(textPart);
            //adiciona a mensagem o conteúdo texto e anexo
            mensagem.setContent(mps);
            mensagem.addHeader("MyHeaderName", "SISCONFIP");

            mensagem.setSentDate(new Date());

            Transport tr = session.getTransport("smtp");
            tr.connect("smtps.bol.com.br", "sisconfip@bol.com.br", "123mudar");
            mensagem.saveChanges();	// don't forget this
            tr.sendMessage(mensagem, mensagem.getAllRecipients());
            tr.close();
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
