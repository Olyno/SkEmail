package com.alexlew.skemail.expressions.EmailServices;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

@Name("IMAP Address of Email Service")
@Description("Returns the imap address of an email service. Can be set in a email service scope")
@Examples({
		"make new email service:",
		"\tset imap address of service to \"imap.myservice.com\""
})
@Since("1.3")

public class ExprIMAPAddressOfService extends SimplePropertyExpression<EmailService, String> {

	static {
		register(ExprIMAPAddressOfService.class, String.class,
				"imap(-|_| )address", "emailservice");
	}

	@Override
	public String convert(EmailService service) {
		return service.getImap_address();
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
					service.setImap_address((String) delta[0]);
					break;
				case DELETE:
					service.setImap_address(null);
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected String getPropertyName() {
		return "imap address";
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}



