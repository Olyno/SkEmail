package com.alexlew.skemail.expressions.EmailCreator;

import org.bukkit.event.Event;

import com.alexlew.skemail.scopes.ScopeEmail;
import com.alexlew.skemail.types.EmailCreator;
import com.alexlew.skemail.util.EffectSection;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Name("Email expression")
@Description("If it isn't inside an email scope, this expression returns a new emailcreator. " +
		"If it is inside of an email scope, it returns the email that belongs to that scope.")
@Examples({
		"# outside a scope",
		"",
		"set {_e} to a new email",
		"",
		"# or in a scope",
		"",
		"make a new email:",
		"\tset object of email to \"TEST\"",
		"\tset body of email to \"Hey! That's a test!\"",
		"\tset receiver of email to \"hey@gmailcom\"",
		"\tadd \"plugins/myfile.txt\" to attachments of email",
		"set {_email} to last email"
})
@Since("1.0")

public class ExprEmail extends SimpleExpression<EmailCreator>{

	static {
		Skript.registerExpression(ExprEmail.class, EmailCreator.class, ExpressionType.SIMPLE,
				"[(the|an|[a] new)] email");
	}

	private boolean scope = false;

	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		scope = EffectSection.isCurrentSection(ScopeEmail.class);
		return scope;
	}

	@Override
	protected EmailCreator[] get(Event e) {
		return new EmailCreator[]{
				scope ? ScopeEmail.lastEmailCreator : new EmailCreator()
		};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends EmailCreator> getReturnType() {
		return EmailCreator.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "the email";
	}
}

