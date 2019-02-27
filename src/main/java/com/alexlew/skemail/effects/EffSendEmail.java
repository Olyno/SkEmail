package com.alexlew.skemail.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.SkEmail;
import org.bukkit.event.Event;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

@Name("Send EmailCreator")
@Description("Send the email")
@Examples({
		"send last email"
})
@Since("1.0")

public class EffSendEmail extends Effect {

	static {
		Skript.registerEffect(EffSendEmail.class,
				"send %email% [to %-string%] [(using|with) (%-session%|%-string%)]");
	}

	private Expression<Message> email;
	private Expression<String> rec;
	private Expression<Object> connection;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		email = (Expression<Message>) expr[0];
		rec = (Expression<String>) expr[1];
		if (expr[2] != null) {
			connection = (Expression<Object>) expr[2];
		}
		return true;
	}

	@Override
	protected void execute(Event e) {
		Message emailObject = email.getSingle(e);
		
		try {
			Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
			Transport.send(emailObject, emailObject.getAllRecipients());

		} catch (MessagingException e1) {
			SkEmail.error("An error occurred. Try to check this link and retry: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail");
			SkEmail.error("If the problem persists, try to reload your server.");
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			SkEmail.error("Impossible to connect to your mail box to send your email. Try to restart your server.");
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "send " + email.toString(e, debug);
	}

	/*public static Message build() {
		try {
			Message message = new MimeMessage(session);
			Multipart multipart = new MimeMultipart();
			message.setFrom(ExprAuthorsOfEmail.emailAuthor);
			message.setSubject(ExprObjectOfEmail.emailObject);
			message.addRecipients(Message.RecipientType.TO, ExprReceiversOfEmail.emailReceivers);
			multipart.addBodyPart(ExprBodyOfEmail.emailBody);
			multipart.addBodyPart(ExprAttachFilesOfEmail.emailAttachments);
			message.setContent(multipart);
			return message;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}*/
	
}
