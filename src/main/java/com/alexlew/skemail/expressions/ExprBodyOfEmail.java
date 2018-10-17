package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilderbase;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

@Name("Body of Email")
@Description("Returns the body of an email. Can be set in a email scope")
@Examples({
			"make new email:",
			"\tset body of email to \"welcome on my new server!\""
	})
@Since("1.0")

public class ExprBodyOfEmail extends SimplePropertyExpression<EmailBuilderbase, String> {

	static {
		register(ExprBodyOfEmail.class, String.class,
				"[the] (body|content)", "emailbuilderbase");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilderbase email) {
		return email.getBody();
	}

	@Override
	protected String getPropertyName() {
		return "body";
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
	    for (EmailBuilderbase email : getExpr().getArray(e)) {
	        switch (mode) {
	            case SET:
	                email.setBody((String) delta[0]);
	                break;
	            case DELETE:
	                email.setBody(null);
			default:
				break;
	        }
	    }
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
	    if (mode == ChangeMode.SET || mode == ChangeMode.DELETE) {
	        return new Class[]{String.class};
	    }
	    return null;
	}
}



