package com.alexlew.skemail.expressions.EmailServices.custom;

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
import com.alexlew.skemail.scopes.ScopeEmailCreator;
import com.alexlew.skemail.scopes.ScopeEmailService;
import com.alexlew.skemail.types.EmailCreator;
import com.alexlew.skemail.types.EmailService;
import com.alexlew.skemail.util.EffectSection;
import org.bukkit.event.Event;

@Name("Email Service expression")
@Description("If it isn't inside an email service scope, this expression returns a new emailservice. " +
		"If it is inside of an email service scope, it returns the email service that belongs to that scope.")
@Examples({
		"# outside a scope",
		"",
		"set {_e} to a new email service",
		"",
		"# or in a scope",
		"",
		"make a new email service:",
		"\tset name of mail service to \"My service\"",
		"\tset smtp address of mail service to \"smtp.myservice.com\"",
		"\tset smtp port of mail service to 465",
		"\tset imap address of mail service to \"imap.myservice.com\"",
		"\tset imap port of mail service to 900",
		"set {_service} to last email service"
})
@Since("1.3")

public class ExprEmailService extends SimpleExpression<EmailService>{

	static {
		Skript.registerExpression(ExprEmailService.class, EmailService.class, ExpressionType.SIMPLE,
				"[(the|an|[a] new)] [[e]mail] service");
	}

	private boolean scope = false;

	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		scope = EffectSection.isCurrentSection(ScopeEmailService.class);
		return scope;
	}

	@Override
	protected EmailService[] get(Event e) {
		return new EmailService[]{
				scope ? ScopeEmailService.lastEmailService : new EmailService()
		};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends EmailService> getReturnType() {
		return EmailService.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "the email service";
	}
}

