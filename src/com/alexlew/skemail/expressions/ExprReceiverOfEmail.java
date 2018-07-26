package com.alexlew.skemail.expressions;

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

public class ExprReceiverOfEmail extends SimplePropertyExpression<EmailBuilder, String> {

	static {
		Skript.registerExpression(ExprReceiverOfEmail.class, String.class, ExpressionType.PROPERTY, "%emailbuilder%'s receiver", "receiver of %emailbuilder%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilder email) {
		return email.getReceiver();
	}

	@Override
	protected String getPropertyName() {
		return "receiver";
	}
	
	   @Override
	   public void change(Event e, Object[] delta, ChangeMode mode){
	       if (delta != null) {
	           EmailBuilder email = getExpr().getSingle(e);
	           if (mode != ChangeMode.SET && mode != ChangeMode.REMOVE && email == null) return;
	           String receiver = (String) delta[0];
	           switch (mode) {
	               case ADD:
	               case DELETE:
	               case REMOVE:
	                   email.setAuthor(null);
	                   break;
	               case REMOVE_ALL:
	               case RESET:
	               case SET:
	                   email.setAuthor(receiver);
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



