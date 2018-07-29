package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import com.alexlew.skemail.scopes.ScopeEmail;
import com.alexlew.skemail.types.EmailBuilder;
import com.alexlew.skemail.util.EffectSection;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Name("Email expression")
@Description("If it isn't inside an email scope, this expression returns a new emailbuilder. " +
        "If it is inside of an email scope, it returns the email that belongs to that scope.")
@Examples({
	"# outside a scope",
    "",
    "set {_e} to a new email",
    "",
    "# or in a scope",
    "",
    "make a new email:",
    "\tset title of email to \"TEST\"",
    "\tset url of the embed to \"https://google.com\"",
    "\tset title of the embed to \"Google!\"",
    "set {_email} to last email"
	})
@Since("1.0")

public class ExprEmail extends SimpleExpression<EmailBuilder>{

	static {
		Skript.registerExpression(ExprEmail.class, EmailBuilder.class, ExpressionType.SIMPLE, "[(the|an|[a] new)] email");
	}
	
	private boolean scope = false;
	
	@Override
	public Class<? extends EmailBuilder> getReturnType() {
		return EmailBuilder.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		scope = EffectSection.isCurrentSection(ScopeEmail.class);
		return scope;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "the email";
	}

	@Override
	protected EmailBuilder[] get(Event e) {
		return new EmailBuilder[]{
                scope ? ScopeEmail.lastEmail : new EmailBuilder()
	};
	}
}

