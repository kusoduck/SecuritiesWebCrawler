package com.kusoduck.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtils {
	private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	private static String propFileName = System.getProperty("prop.mail");
	private static Properties prop = new Properties();

	private EmailUtils() {

	}

	static {
		try {
			FileReader fileReader = new FileReader(propFileName);
			prop.load(fileReader);
		} catch (IOException e) {
			logger.error("Mail property file loading fail", e);
		}
	}

	public static void sendFromGmail(String account, String password, String from, String to, String subject,String text) {
		logger.debug("start sendFromGmail");

		if (StringUtils.isNotBlank(from) && StringUtils.isNotBlank(to)) {
			// Assuming you are sending email from through gmails smtp
			String host = "smtp.gmail.com";

			// Get system properties
			Properties properties = System.getProperties();

			// Setup mail server
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.auth", "true");

			// Get the Session object.// and pass
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(account, password);
				}
			});
			// session.setDebug(true);
			try {
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(from));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				String cc = prop.getProperty("cc");
				if (StringUtils.isNotBlank(cc)) {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
				}
				String bcc = prop.getProperty("bcc");
				if (StringUtils.isNotBlank(bcc)) {
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
				}

				// Set Subject: header field
				message.setSubject(subject);

				Multipart multipart = new MimeMultipart();
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setText(text);
				multipart.addBodyPart(textPart);

//			MimeBodyPart attachmentPart = new MimeBodyPart();
//			File file = new File(System.getProperty("output.file"));
//			attachmentPart.attachFile(file);
//			multipart.addBodyPart(attachmentPart);

				message.setContent(multipart);

				// Send message
				Transport.send(message);
				logger.info("Sent mail successfully");
			} catch (MessagingException e) {
				logger.error("There is a problem with the message", e);
			}
		} else {
			logger.warn("Mail setting error");
		}

		logger.debug("end sendFromGmail");
	}
}
