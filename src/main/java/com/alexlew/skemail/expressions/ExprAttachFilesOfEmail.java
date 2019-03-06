package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Name("Attach files of Email")
@Description("Returns attach files of an email. Can be set in a email scope")
@Examples({
		"make new email:",
		"\tset attachment of email to \"plugin/myfile.txt\""
})
@Since("1.1")

public class ExprAttachFilesOfEmail extends SimpleExpression<String> {
	
	public static List<String> files = new ArrayList<>();
	
	static {
		Skript.registerExpression(ExprAttachFilesOfEmail.class, String.class, ExpressionType.COMBINED,
				"%email%['s] (attached file|attachment)[s]",
				"(attached file|attachment)[s] of %email%");
	}
	
	private Expression<Message> message;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		message = (Expression<Message>) expr[0];
		return true;
	}
	
	@Override
	protected String[] get( Event e ) {
		return files.toArray(new String[files.size()]);
	}
	
	@Override
	public Class<?>[] acceptChange( final Changer.ChangeMode mode ) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.DELETE) {
			return new Class[]{String.class};
		}
		return null;
	}
	
	@Override
	public void change( Event e, Object[] delta, Changer.ChangeMode mode ) {
		try {
			Message email = message.getSingle(e);
			Multipart multipart = new MimeMultipart();
			MimeMultipart ePart = (MimeMultipart) email.getContent();
			for (int i = 0; i < ePart.getCount(); i++) {
				if (!Part.ATTACHMENT.equalsIgnoreCase(ePart.getBodyPart(i).getDisposition())) {
					multipart.addBodyPart(ePart.getBodyPart(i));
					break;
				}
			}
			for (Object o : delta) {
				String file = (String) o;
				switch (mode) {
					case SET:
						files = new ArrayList<>(Arrays.asList(file));
						break;
					case DELETE:
						files = new ArrayList<>();
						break;
					case ADD:
						files.add(file);
						break;
					case REMOVE:
						files.remove(file);
						break;
					default:
						break;
				}
			}
			
			for (String f : files.toArray(new String[files.size()])) {
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.attachFile(f);
				multipart.addBodyPart(attachPart);
			}
			
			email.setContent(multipart);
			
		} catch (MessagingException | IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@Override
	public String toString( Event e, boolean debug ) {
		//return "attach file " + email.toString(e, debug);
		return null;
	}
	
}
