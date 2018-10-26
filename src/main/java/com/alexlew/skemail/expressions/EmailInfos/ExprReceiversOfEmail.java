package com.alexlew.skemail.expressions.EmailInfos;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("Author of Email")
@Description("Returns the author of an email. Can be set in a email scope")
@Examples({
		"set {_receivers::*} to receivers of {_email}"
})
@Since("1.2")

public class ExprReceiversOfEmail extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprReceiversOfEmail.class, String.class, ExpressionType.SIMPLE,
				"%emailreader%['s] receiver[s]",
				"receiver[s] of %emailreader%");
	}

	private Expression<javax.mail.Message> email;

	@Override
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		email = (Expression<javax.mail.Message>) expr[0];
		return true;
	}

	@Override
	protected String[] get( Event e ) {
		Message email = this.email.getSingle(e);
		if (email == null) {
			return null;
		}
		List<String> toAddresses = new ArrayList<String>();
		Address[] recipients = new Address[0];
		try {
			recipients = email.getRecipients(Message.RecipientType.TO);
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		if (recipients.length > 0) {
			for (Address address : recipients) {
				toAddresses.add(address.toString());
			}
		}
		return toAddresses.toArray(new String[0]);
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
		return "receivers " + email.toString(e, debug);
	}

}



