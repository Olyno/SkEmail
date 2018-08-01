package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilderbase;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;

@Name("Object of Email")
@Description("Returns the object of an email. Can be set in a email scope")
@Examples({
			"make new email:",
			"\tset object of email to \"Test\""
	})
@Since("1.0")

public class ExprObjectOfEmail extends SimplePropertyExpression<EmailBuilderbase, String> {

	static {
		Skript.registerExpression(ExprObjectOfEmail.class, String.class, ExpressionType.PROPERTY, 
				"%emailbuilderbase%'s object", "object of %emailbuilderbase%",
				"%emailbuilderbase%'s subject", "subject of %emailbuilderbase%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilderbase email) {
		return email.getObject();
	}

	@Override
	protected String getPropertyName() {
		return "object";
	}
	
	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
	    for (EmailBuilderbase email : getExpr().getArray(e)) {    
	        switch (mode) {
	            case SET:
	                email.setObject((String) delta[0]);
	                break;
	            case DELETE:
	                email.setObject(null);
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



