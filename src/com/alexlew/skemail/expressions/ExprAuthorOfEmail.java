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
			"\tset author of email to \"admin@gmail.com\""
	})
@Since("1.0")

public class ExprAuthorOfEmail extends SimplePropertyExpression<EmailBuilderbase, String> {

	static {
		Skript.registerExpression(ExprAuthorOfEmail.class, String.class, ExpressionType.PROPERTY, 
				"%emailbuilderbase%'s author", "author of %emailbuilderbase%",
				"%emailbuilderbase%'s from part", "from part of %emailbuilderbase%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilderbase email) {
		return email.getAuthor();
	}

	@Override
	protected String getPropertyName() {
		return "author";
	}
	
	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
	    for (EmailBuilderbase email : getExpr().getArray(e)) {    
	        switch (mode) {
	            case SET:
	                email.setAuthor((String) delta[0]);
	                break;
	            case DELETE:
	                email.setAuthor(null);
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



