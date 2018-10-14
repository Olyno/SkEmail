package com.alexlew.skemail.effects;

import java.util.Properties;

import ch.njol.skript.doc.Name;
import org.bukkit.event.Event;

import javax.mail.*;
import javax.mail.internet.*;

import com.alexlew.skemail.types.EmailBuilderbase;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Send EmailBuilderbase")
@Description("Send the email")
@Examples({
			"send last email"
	})
@Since("1.0")

public class EffSendLastEmail extends Effect {

	static {
		Skript.registerEffect(EffSendLastEmail.class, 
				"send %emailbuilderbase%");
	}
	
	private Expression<EmailBuilderbase> email;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		email = (Expression<EmailBuilderbase>) expr[0];
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "send " + e.toString();
	}

	@Override
	protected void execute(Event e) {
		String receiver = email.getSingle(e).getReceiver();
		String object = email.getSingle(e).getObject();
		String body = email.getSingle(e).getBody();
		String html_content = email.getSingle(e).getHtml_content();
		
		if (receiver != null) {
			if(object!=null) {
				if(body==null) {
					Skript.warning("It is recommended to put a content to your email");
				}

				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				Session session = Session.getDefaultInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(EffConnexion.username + "@gmail.com", EffConnexion.password);
							}
						});

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(EffConnexion.username + "@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(receiver));
					message.setSubject(object);
					message.setText(body);

					Transport.send(message);

				} catch (MessagingException e1) {
					//throw new RuntimeException(e1);
					Skript.warning("nah");
				}


			} else {
				Skript.error("You must precise the object/subject of your email!");
			}
			
		} else {
			Skript.error("You must precise the mail which will receive your email!");
		
		}
}

}
