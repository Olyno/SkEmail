package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import com.alexlew.skemail.scopes.ScopeEmail;
import com.alexlew.skemail.types.EmailBuilderbase;
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
@Description("If it isn't inside an email scope, this expression returns a new emailbuilderbase. " +
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
    "set {_email} to last email"
	})
@Since("1.0")

public class ExprEmail extends SimpleExpression<EmailBuilderbase>{

	static {
		Skript.registerExpression(ExprEmail.class, EmailBuilderbase.class, ExpressionType.SIMPLE, 
				"[(the|an|[a] new)] email");
	}
	
	private boolean scope = false;
	
	@Override
	public Class<? extends EmailBuilderbase> getReturnType() {
		return EmailBuilderbase.class;
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
	protected EmailBuilderbase[] get(Event e) {
		return new EmailBuilderbase[]{
                scope ? ScopeEmail.lastEmail : new EmailBuilderbase()
	};
	}
}

