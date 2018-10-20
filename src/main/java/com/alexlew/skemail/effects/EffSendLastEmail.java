package com.alexlew.skemail.effects;

import java.io.IOException;
import java.util.Properties;

import ch.njol.skript.doc.Name;
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
				"send %emailcreator% [to %-string%]");
	}

	private Expression<EmailCreator> email;
	private Expression<String> rec;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		email = (Expression<EmailCreator>) expr[0];
		rec = (Expression<String>) expr[1];
		if (email == null) {
			return false;
		}
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "send " + email.toString(e, debug);
	}

	@Override
	protected void execute(Event e) {
		String[] receivers = email.getSingle(e).getReceivers();
		String object = email.getSingle(e).getObject();
		String body = email.getSingle(e).getBody();
		String[] attach_files = email.getSingle(e).getAttachments();

		if (receivers != null) {
			if(object!=null) {
				if(body==null) {
					Skript.warning("It is recommended to put a content to your email");
				}

				Properties props = new Properties();
				props.put("mail.smtp.host", EffConnection.smtp_address);
				props.put("mail.smtp.socketFactory.port", EffConnection.smtp_port);
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", EffConnection.smtp_port);

				Session session = Session.getDefaultInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(EffConnection.username, EffConnection.password);
							}
						});

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(EffConnection.username));
					message.setSubject(object);

					BodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(body, "text/html");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);

					if (attach_files != null && attach_files.length > 0) {
						for (String filePath : attach_files) {
							MimeBodyPart attachPart = new MimeBodyPart();

							try {
								attachPart.attachFile(filePath);
							} catch (IOException e1) {
								Skript.error("[SkEmail] The file path of file (" + filePath + ") doesn't exist or is bad.");
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
					Skript.error("[SkEmail] An error occurred due to a wrong code :(");
				}


			} else {
				Skript.error("[SkEmail] You must precise the object/subject of your email!");
			}

		} else {
			Skript.error("[SkEmail] You must precise the mail which will receive your email!");

		}
	}

}
