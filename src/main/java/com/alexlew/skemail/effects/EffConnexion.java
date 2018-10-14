package com.alexlew.skemail.effects;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Connexion")
@Description("Connect to your email account")
@Examples({
			"login to \"user\" with \"pass\""
	})
@Since("1.0")

public class EffConnexion extends Effect {

	static {
		Skript.registerEffect(EffConnexion.class, 
				"(connexion|login) to [user] %string% (and|with) [pass[word]] %string%");
	}
	
	public static String username;
	public static String password;
	
	public Expression<String> user;
	public Expression<String> pass;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		user = (Expression<String>) expr[0];
		pass = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "connexion to user " + user.toString(e, debug) + " and password " + pass.toString(e, debug);
	}

	@Override
	protected void execute(Event e) {
		username = user.getSingle(e);
		password = pass.getSingle(e);
		username.replaceAll("@gmail", "");
		System.out.print("Connection established!");
	}

}
