package com.alexlew.skemail.expressions.EmailCreator;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailCreator;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.ExpressionType;

@Name("Receiver of Email")
@Description("Returns the author of an email. Can be set in a email scope")
@Examples({
		"make new email:",
		"\tset receiver of email to \"myfriend@gmail.com\""
})
@Since("1.0")

public class ExprReceiverOfEmail extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprReceiverOfEmail.class, String.class, ExpressionType.SIMPLE,
				"%emailcreator%['s] receiver[s]",
				"receiver[s] of %emailcreator%");
	}

	private Expression<EmailCreator> email;

	@Override
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		email = (Expression<EmailCreator>) expr[0];
		return true;
	}

	@Override
	protected String[] get( Event e ) {
		EmailCreator email = this.email.getSingle(e);
		if (email == null) {
			return null;
		}
		String[] files = email.getReceivers();
		return files;
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.DELETE) {
			return new Class[]{String.class};
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		for (EmailCreator email : email.getArray(e)) {
			switch (mode) {
				case SET:
					email.setReceiver((String) delta[0]);
					break;
				case DELETE:
					email.setReceiver(null);
					break;
				case ADD:
					email.addReceiver((String) delta[0]);
					break;
				case REMOVE:
					email.removeReceiver((String) delta[0]);
					break;
				default:
					break;
			}
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
		return "receiver " + email.toString(e, debug);
	}

}



