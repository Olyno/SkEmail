package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
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
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

@Name("Service")
@Description("Returns the specified mail service. This service must to be loaded before use it.")
@Examples({
		"command /login:\n" +
				"\ttrigger:\n" +
				"\t\tset {_service} to new gmail service\n" +
				"\t\tlogin to {_service} from mail \"myemail@gmail.com\" and password \"my password\""
})
@Since("1.5")

public class ExprService extends SimpleExpression<EmailService> {

	static {
		Skript.registerExpression(ExprService.class, EmailService.class, ExpressionType.SIMPLE,
				"[new] <\\w+> (service|session)");
	}

	private String serviceName;

	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		serviceName = parseResult.regexes.get(0).group().toLowerCase();
		return true;
	}

	@Override
	protected EmailService[] get(Event e) {
		if (EmailService.services.containsKey(serviceName)) {
			return new EmailService[]{EmailService.services.get(serviceName)};
		} else {
			SkEmail.error("This service doesn't exist: " + serviceName);
			return null;
		}
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
		return "Email service " + serviceName;
	}
}
