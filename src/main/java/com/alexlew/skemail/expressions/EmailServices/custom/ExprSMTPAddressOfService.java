package com.alexlew.skemail.expressions.EmailServices.custom;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

@Name("SMTP Address of Email Service")
@Description("Returns the smtp address of an email service. Can be set in a email service scope")
@Examples({
		"make new email service:",
		"\tset smtp address of service to \"smtp.myservice.com\""
})
@Since("1.3")

public class ExprSMTPAddressOfService extends SimplePropertyExpression<EmailService, String> {

	static {
		register(ExprSMTPAddressOfService.class, String.class,
				"smtp(-|_| )address", "emailservice");
	}

	@Override
	public String convert(EmailService service) {
		return service.getSmtp_address();
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE) {
			return new Class[]{String.class};
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		for (EmailService service : getExpr().getArray(e)) {
			switch (mode) {
				case SET:
					service.setSmtp_address((String) delta[0]);
					break;
				case DELETE:
					service.setSmtp_address(null);
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected String getPropertyName() {
		return "smtp address";
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}



