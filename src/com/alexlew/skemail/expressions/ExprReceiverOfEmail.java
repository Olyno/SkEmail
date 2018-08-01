package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilderbase;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;

@Name("Author of Email")
@Description("Returns the author of an email. Can be set in a email scope")
@Examples({
			"make new email:",
			"\tset receiver of email to \"admin@gmail.com\""
	})
@Since("1.0")

public class ExprReceiverOfEmail extends SimplePropertyExpression<EmailBuilderbase, String> {

	static {
		Skript.registerExpression(ExprReceiverOfEmail.class, String.class, ExpressionType.PROPERTY, 
				"%emailbuilderbase%'s receiver", "receiver of %emailbuilderbase%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilderbase email) {
		return email.getReceiver();
	}

	@Override
	protected String getPropertyName() {
		return "receiver";
	}
	
	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
	    for (EmailBuilderbase email : getExpr().getArray(e)) {    
	        switch (mode) {
	            case SET:
	                email.setReceiver((String) delta[0]);
	                break;
	            case DELETE:
	                email.setReceiver(null);
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



