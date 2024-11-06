package es.ingenieria.prog3.proyecto.gui.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private static final String SMTP_SERVER = "smtp.sendgrid.net"; // SendGrid's SMTP server
    private static final String SMTP_PORT = "587"; // Use port 587 for TLS, or 465 for SSL
    private static final String USERNAME = "apikey";
    private static final String PASSWORD = "SG.cItFsi9cQFmmIO08hcqK8g.pvPg" + "LJ7pQKJrVIsSmnV8L7n-3AJZ9b002aXz3cxB5Ls";
    
    public static void sendEmail(String toEmail, String subject, String messageContent) {
        // Configure SMTP server settings
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", SMTP_PORT);

        // Authenticate with the SMTP server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hotelhostdeusto@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageContent);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email.");
        }
    }
}

// IAG: CHATGPT (Toda la clase)
// Modificación: Si