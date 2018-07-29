package com.alexlew.skemail.expressions;

import java.io.File;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilder;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.coll.CollectionUtils;

@Name("Author of Email")
@Description("Returns the author of an email. Can be set in a email scope")
@Examples({
			"make new email:",
			"\tset receiver of email to \"admin@gmail.com\""
	})
@Since("1.0")

public class ExprAttachFileOfEmail extends SimplePropertyExpression<EmailBuilder, File> {

	static {
		Skript.registerExpression(ExprAttachFileOfEmail.class, File.class, ExpressionType.PROPERTY, "%emailbuilder%'s attach file", "attach file of %emailbuilder%");
	}

	@Override
	public Class<? extends File> getReturnType() {
		return File.class;
	}

	@Override
	public File convert(EmailBuilder email) {
		return email.getAttach_file();
	}

	@Override
	protected String getPropertyName() {
		return "attach file";
	}
	
	   @Override
	   public void change(Event e, Object[] delta, ChangeMode mode){
	       if (delta != null) {
	           EmailBuilder email = getExpr().getSingle(e);
	           if (mode != ChangeMode.SET && mode != ChangeMode.REMOVE && email == null) return;
	           String pth = (String) delta[0];
	           switch (mode) {
	               case ADD:
	               case DELETE:
	               case REMOVE:
	                   email.setAttach_file(null);
	                   break;
	               case REMOVE_ALL:
	               case RESET:
	               case SET:
	            	   File file = new File(pth);
	                   email.setAttach_file(file);
	                   break;
	               default:
	                   assert false;
	           }
	       }
	   }
	 
	   @Override
	   public Class<?>[] acceptChange(final ChangeMode mode) {
	       return (mode != ChangeMode.REMOVE_ALL) ? CollectionUtils.array(Number.class) :  null;
	   }
}



