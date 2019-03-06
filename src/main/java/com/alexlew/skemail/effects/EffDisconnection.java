package com.alexlew.skemail.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.SkEmail;
import org.bukkit.event.Event;

import javax.mail.MessagingException;

@Name("Disconnection")
@Description("Disconnection from one of your connected account.")
@Examples({
		"disconnect \"myemail@gmail.com\" from all connected mails"
})
@Since("1.5")

public class EffDisconnection extends Effect {
	
	static {
		Skript.registerEffect(EffDisconnection.class,
				"(logout|disconnect) %string% [from [all] connected [e]mail[s]]"
		);
	}
	
	private Expression<String> login;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		login = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	protected void execute( Event e ) {
		String account = login.getSingle(e);
		if (account.replaceAll(" ", "") != "") {
			if (EffConnection.accounts.containsKey(account)) {
				try {
					EffConnection.accounts.get(account).getTransport().close();
					EffConnection.accounts.remove(account);
				} catch (MessagingException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			SkEmail.error("You must to put a email address to remove it from all connected mails.");
		}
	}
	
	@Override
	public String toString( Event e, boolean debug ) {
		String username =
				login.getSingle(e) != null ?
						login.getSingle(e).isBlank() ?
								"null"
								: login.getSingle(e)
						: "null";
		return "Disconnection from \"" + username + "\"";
	}
}
