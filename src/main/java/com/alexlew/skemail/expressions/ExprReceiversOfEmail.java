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
import com.alexlew.skemail.SkEmail;
import org.bukkit.event.Event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Name("Receiver/Recipients of Email")
@Description("Returns the receivers/recipients of an email. Can be set in a email scope")
@Examples({
		"make new email:",
		"\tset receiver of email to \"myfriend@gmail.com\""
})
@Since("1.0")

public class ExprReceiversOfEmail extends SimpleExpression<Address> {

	static {
		Skript.registerExpression(ExprReceiversOfEmail.class, Address.class, ExpressionType.SIMPLE,
				"%email%'s (receiver|recipient)[s]",
				"(receiver|recipient)[s] of %email%");
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
					if (delta.length > 1) {
						email.setRecipients(Message.RecipientType.TO,
								addresses.toArray(new InternetAddress[addresses.size()]));
					} else {
						email.setRecipient(Message.RecipientType.TO, new InternetAddress((String) delta[0]));
					}
					break;
				case ADD:
					for (Object o1 : delta) {
						InternetAddress address = null;
						if (o1 instanceof String) {
							address = new InternetAddress((String) o1);
						} else if (o1 instanceof InternetAddress) {
							address = (InternetAddress) o1;
						} else {
							SkEmail.error("You can't add a receiver (recipient) of type " + o1.getClass().getName() + " because it's not a type String or InternetAddress.");
						}
						address.validate();
						addresses.add(address);
					}
					email.setRecipients(Message.RecipientType.TO, addresses.toArray(new InternetAddress[addresses.size()]));
					break;
				case REMOVE:
					for (Object o : delta) {
						InternetAddress address = null;
						if (o instanceof String) {
							address = new InternetAddress((String) o);
						} else if (o instanceof InternetAddress) {
							address = (InternetAddress) o;
						} else {
							SkEmail.error("You can't add a receiver (recipient) of type " + o.getClass().getName() + " because it's not a type String or InternetAddress.");
						}
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
		return "Receivers of email";
	}

}



