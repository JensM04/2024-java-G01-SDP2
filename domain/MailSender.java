package domain;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

		public static void stuurEmail(String content, String subject, String receiver) {


			String host = "smtp.gmail.com";

			Authenticator authenticator = new SMTPAuthenticator();

			Properties props = new Properties();

			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.quitwait", "false");
			props.put("mail.smtp.socketFactory.port", "587");
			props.put("mail.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");

			props.setProperty("mail.transport.protocol", "smtp");

			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.ssl.trust", "*");
			props.setProperty("mail.smtp.starttls.enable", "true");//True or False
			props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
			props.setProperty("mail.smtp.timeout", "300000");
			props.setProperty("mail.smtp.connectiontimeout", "300000");
			props.setProperty("mail.smtp.writetimeout", "300000");

			try {
				Session session = Session.getInstance(props, authenticator);
				Message message = new MimeMessage(session);

				message.setFrom(new InternetAddress("spd2.g01@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

				message.setSubject(subject);

				message.setContent(content, "text/html");

				ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		        executor.execute(new Runnable() {
		            @Override
		            public void run() {
		                try {
							Transport.send(message);
							System.out.println("Mail succesfully sent");
						} catch (MessagingException e) {
							e.printStackTrace();
						}
		            }
		        });
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
}
