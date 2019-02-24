package com.alexlew.skemail.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.SkEmail;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.ExpressionType;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Name("Receiver of Email")
@Description("Returns the receivers of an email. Can be set in a email scope")
@Examples({
		"make new email:",
		"\tset receiver of email to \"myfriend@gmail.com\""
})
@Since("1.0")

public class ExprReceiversOfEmail extends SimpleExpression<Address> {

	static {
		Skript.registerExpression(ExprReceiversOfEmail.class, Address.class, ExpressionType.SIMPLE,
				"%email%'s receiver[s]",
				"receiver[s] of %email%");
	}

	private Expression<Message> message;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		message = (Expression<Message>) expr[0];
		return true;
	}

	@Override
	protected Address[] get( Event e ) {
		try {
			Message email = message.getSingle(e);
			return email.getAllRecipients();
		} catch (MessagingException e1) {
			return null;
		}
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.DELETE) {
			return new Class[]{String.class};
		}
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Message email = message.getSingle(e);
		try {
			List<Address> addresses = new LinkedList<>();
			if (email.getRecipients(Message.RecipientType.TO) != null) {
				addresses.addAll(Arrays.asList(email.getRecipients(Message.RecipientType.TO)));
			}
			
			switch (mode) {
				case SET:
					email.setRecipient(Message.RecipientType.TO, new InternetAddress((String) delta[0]));
					break;
				case ADD:
					for (Object o1 : delta) {
						InternetAddress address = new InternetAddress((String) o1);
						address.validate();
						addresses.add(address);
					}
					email.setRecipients(Message.RecipientType.TO, addresses.toArray(new InternetAddress[addresses.size()]));
					break;
				case REMOVE:
					for (Object o : delta) {
						InternetAddress address = new InternetAddress((String) o);
						address.validate();
						addresses.remove(address);
					}
					email.setRecipients(Message.RecipientType.TO, addresses.toArray(new InternetAddress[addresses.size()]));
					break;
				case DELETE:
					email.setRecipient(Message.RecipientType.TO, null);
					break;
				default:
					break;
			}
		} catch (AddressException e1) {
			SkEmail.error("This email address is incorrect: " + delta[0]);
		} catch (MessagingException ignored) { }
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends InternetAddress> getReturnType() {
		return InternetAddress.class;
	}

	@Override
	public String toString( Event e, boolean debug ) {
		return null;//return "receiver " + email.toString(e, debug);
	}

}



