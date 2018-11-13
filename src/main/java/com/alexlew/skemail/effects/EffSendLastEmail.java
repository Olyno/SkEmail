package com.alexlew.skemail.effects;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.alexlew.skemail.SkEmail;
import com.alexlew.skemail.types.EmailConnection;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

import javax.mail.*;
import javax.mail.internet.*;

import com.alexlew.skemail.types.EmailCreator;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Send EmailCreator")
@Description("Send the email")
@Examples({
		"send last email"
})
@Since("1.0")

public class EffSendLastEmail extends Effect {

	static {
		Skript.registerEffect(EffSendLastEmail.class,
				"send %emailcreator% [to %-string%] [using %-emailconnection%]");
	}

	private Expression<EmailCreator> email;
	private Expression<String> rec;
	private Expression<EmailConnection> connection;

	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		email = (Expression<EmailCreator>) expr[0];
		rec = (Expression<String>) expr[1];
		if (expr[2] instanceof EmailConnection)	{
			connection = (Expression<EmailConnection>) expr[2];
		}

		return true;
	}

	@Override
	protected void execute(Event e) {
		String[] receivers = rec == null ? email.getSingle(e).getReceivers() : new String[] {rec.getSingle(e)};
		EmailConnection connect = connection == null ? EffConnection.lastEmailConnection : connection.getSingle(e);
		String object = email.getSingle(e).getObject();
		String body = email.getSingle(e).getBody();
		String author = email.getSingle(e).getAuthor();
		String[] attach_files = email.getSingle(e).getAttachments();
		String username = connect.getUsername();
		String password = connect.getPassword();
		EmailService service = connect.getService();

		if (receivers != null) {
			if(object!=null) {
				if(body!=null) {
					body = body.replace("%nl%", "<br>");
					body = body.replace("%new line%", "<br>");
					body = body.replace("%newline%", "<br>");

					Properties props = new Properties();
					props.put("mail.smtp.host", service.getSmtp_address());
					props.put("mail.smtp.socketFactory.port", service.getSmtp_port());
					props.put("mail.smtp.socketFactory.class",
							"javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", service.getSmtp_port());

					Session session = Session.getDefaultInstance(props,
							new javax.mail.Authenticator() {
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(username, password);
								}
							});

					try {

						Message message = new MimeMessage(session);
						if (author != null) {
							message.setFrom(new InternetAddress(username, author));
						} else {
							message.setFrom(new InternetAddress(username));
						}
						message.setSubject(object);

						BodyPart messageBodyPart = new MimeBodyPart();
						messageBodyPart.setContent(body, "text/html; charset=UTF-8");

						Multipart multipart = new MimeMultipart();
						multipart.addBodyPart(messageBodyPart);

						if (attach_files != null && attach_files.length > 0) {
							for (String filePath : attach_files) {
								MimeBodyPart attachPart = new MimeBodyPart();

								try {
									attachPart.attachFile(filePath);
								} catch (IOException e1) {
									SkEmail.error("The file path of file (" + filePath + ") doesn't exist or is bad.");
									//e1.printStackTrace();
								}

								multipart.addBodyPart(attachPart);
							}
						}

						if (receivers != null && receivers.length > 0) {
							for (String receiver : receivers) {
								if (receiver.contains("@")) {
									message.addRecipients(Message.RecipientType.TO,
											InternetAddress.parse(receiver));
								} else {
									Skript.warning("[SkEmail] This receiver is not valid: " + receiver);
								}

							}

						}
						message.setContent(multipart);

						Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
						Transport.send(message);

					} catch (MessagingException e1) {
						SkEmail.error("An error occurred. Try to check this link and retry: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail \nIf the problem persists, try to reload your server.");
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						SkEmail.error("One or more characters are not supported in your name. Try to use ASCII format for the author's name.");
						//e1.printStackTrace();
					}

				} else {
					SkEmail.error("You must add content in your email!");
				}

			} else {
				SkEmail.error("You must precise the object/subject of your email!");
			}

		} else {
			SkEmail.error("You must precise the mail which will receive your email!");

		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "send " + email.toString(e, debug);
	}

}
