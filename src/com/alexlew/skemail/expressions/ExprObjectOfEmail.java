package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilder;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.coll.CollectionUtils;

@Name("Object of Email")
@Description("Returns the object of an email. Can be set in a email scope")
@Examples({
			"make new email:",
			"\tset object of email to \"Test\""
	})
@Since("1.0")

public class ExprObjectOfEmail extends SimplePropertyExpression<EmailBuilder, String> {

	static {
		Skript.registerExpression(ExprObjectOfEmail.class, String.class, ExpressionType.PROPERTY, "%emailbuilder%'s object", "object of %emailbuilder%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilder email) {
		return email.getObject();
	}

	@Override
	protected String getPropertyName() {
		return "object";
	}
	
	   @Override
	   public void change(Event e, Object[] delta, ChangeMode mode){
	       if (delta != null) {
	           EmailBuilder email = getExpr().getSingle(e);
	           if (mode != ChangeMode.SET && mode != ChangeMode.REMOVE && email == null) return;
	           String object = (String) delta[0];
	           switch (mode) {
	               case ADD:
	               case DELETE:
	               case REMOVE:
	                   email.setObject(null);
	                   break;
	               case REMOVE_ALL:
	               case RESET:
	               case SET:
	                   email.setObject(object);
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



