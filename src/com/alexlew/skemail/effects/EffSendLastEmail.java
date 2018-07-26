package com.alexlew.skemail.effects;

import java.io.File;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilder;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Send EmailBuilder")
@Description("Send the email")
@Examples({
			"send last email"
	})
@Since("1.0")

public class EffSendLastEmail extends Effect {

	static {
		Skript.registerEffect(EffSendLastEmail.class, "send %emailbuilder%");
	}
	
	private Expression<EmailBuilder> email;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		email = (Expression<EmailBuilder>) expr[0];
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "send " + e.toString();
	}

	@SuppressWarnings("unused")
	@Override
	protected void execute(Event e) {
		String author = ((EmailBuilder) email).getAuthor();
		String receiver = ((EmailBuilder) email).getReceiver();
		String object = ((EmailBuilder) email).getObject();
		String body = ((EmailBuilder) email).getBody();
		File attach_file = ((EmailBuilder) email).getAttach_file();
		
		Skript.warning("author: " + author + "; receiver: " + receiver + "; object: " + object + "; body: " + body + "; Attach_file: " + attach_file);
		
	}

}
