package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilderbase;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;

@Name("Html Content of Email")
@Description("Returns the html content of an email. Can be set in a email scope")
@Examples({
			"make new email:",
			"\tset html content of email to \"welcome on my new server!\""
	})
@Since("1.0")

public class ExprHtmlContentOfEmail extends SimplePropertyExpression<EmailBuilderbase, String> {

	static {
		Skript.registerExpression(ExprHtmlContentOfEmail.class, String.class, ExpressionType.PROPERTY, 
				"%emailbuilderbase%'s html content", "html content of %emailbuilderbase%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilderbase email) {
		return email.getHtml_content();
	}

	@Override
	protected String getPropertyName() {
		return "html content";
	}
	
	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
	    for (EmailBuilderbase email : getExpr().getArray(e)) {    
	        switch (mode) {
	            case SET:
	                email.setHtml_content((String) delta[0]);
	                break;
	            case DELETE:
	                email.setHtml_content(null);
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



