package com.AlexLew.SkEmail.Skript.Expressions;

import org.bukkit.event.Event;

import com.AlexLew.SkEmail.Skript.Types.EmailBuilder;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.coll.CollectionUtils;

@Name("Body of Email")
@Description("Returns the body of an email. Can be set in a email scope")
@Examples({
			"make new email:",
			"\tset body of email to \"welcome on my new server!\""
	})
@Since("1.0")

public class ExprBodyOfEmail extends SimplePropertyExpression<EmailBuilder, String> {

	static {
		Skript.registerExpression(ExprBodyOfEmail.class, String.class, ExpressionType.PROPERTY, "%emailbuilder%'s body", "body of %emailbuilder%");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(EmailBuilder email) {
		return email.getAuthor();
	}

	@Override
	protected String getPropertyName() {
		return "body";
	}
	
	   @Override
	   public void change(Event e, Object[] delta, ChangeMode mode){
	       if (delta != null) {
	           EmailBuilder email = getExpr().getSingle(e);
	           if (mode != ChangeMode.SET && mode != ChangeMode.REMOVE && email == null) return;
	           String body = (String) delta[0];
	           switch (mode) {
	               case ADD:
	               case DELETE:
	               case REMOVE:
	                   email.setAuthor(null);
	                   break;
	               case REMOVE_ALL:
	               case RESET:
	               case SET:
	                   email.setAuthor(body);
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



