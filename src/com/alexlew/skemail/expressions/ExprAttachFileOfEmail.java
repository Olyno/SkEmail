package com.alexlew.skemail.expressions;

import java.io.File;

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

public class ExprAttachFileOfEmail extends SimplePropertyExpression<EmailBuilderbase, File> {

	static {
		Skript.registerExpression(ExprAttachFileOfEmail.class, File.class, ExpressionType.PROPERTY, 
				"%emailbuilderbase%'s attach file", "attach file of %emailbuilderbase%");
	}

	@Override
	public Class<? extends File> getReturnType() {
		return File.class;
	}

	@Override
	public File convert(EmailBuilderbase email) {
		return email.getAttach_file();
	}

	@Override
	protected String getPropertyName() {
		return "attach file";
	}
	
	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
	    for (EmailBuilderbase email : getExpr().getArray(e)) {    
	        switch (mode) {
	            case SET:
	            	File file = new File((String) delta[0]);
	                email.setAttach_file(file);
	                break;
	            case DELETE:
	                email.setAttach_file(null);
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



