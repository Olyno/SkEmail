package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

@Name("Body of Email")
@Description("Returns the body of an email. Can be set in a email scope")
@Examples({
		"make new email:",
		"\tset body of email to \"welcome on my new server!\""
})
@Since("1.0")

public class ExprBodyOfEmail extends SimplePropertyExpression<Message, String> {

	static {
		register(ExprBodyOfEmail.class, String.class,
				"(body|content)", "email");
	}
	
	@Override
	public String convert( Message email) {
		try {
			MimeMultipart multipart = (MimeMultipart) email.getContent();
			for (int i = 0; i < multipart.getCount(); i++) {
				if (multipart.getBodyPart(i).getContentType().equals("text/plain")) {
					return multipart.getBodyPart(i).getContent().toString();
				}
			}
			return null;
			
		} catch (IOException | MessagingException e1) {
			return null;
		}
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE) {
			return new Class[]{String.class};
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		Multipart multipart = new MimeMultipart();
		BodyPart body = new MimeBodyPart();
		for (Message email : getExpr().getArray(e)) {
			switch (mode) {
				case SET:
					try {
						MimeMultipart current = (MimeMultipart) email.getContent();
						for (int i = 0; i < current.getCount(); i++) {
							if (!current.getBodyPart(i).getContentType().equals("text/plain")) {
								multipart.addBodyPart(current.getBodyPart(i));
							}
						}
					} catch (MessagingException | IOException ignored) { }
					try {
						body.setContent(
								((String) delta[0]).replaceAll("\n", "<br>"),
								"text/html; charset=UTF-8"
						);
						multipart.addBodyPart(body);
						email.setContent(multipart);
						break;
					} catch (MessagingException ignored) { }
					case DELETE:
						try {
							email.setContent(null, "text/html; charset=UTF-8");
							break;
						} catch (MessagingException ignored) { }
					default:
						break;
				}
			}
	}
		
		@Override
	protected String getPropertyName() {
		return "body";
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}



