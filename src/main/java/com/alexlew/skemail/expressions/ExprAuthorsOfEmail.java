package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.SkEmail;
import org.bukkit.event.Event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

@Name("Author/From part of Email")
@Description("Returns the author/from part of an email. Can be set in a email scope")
@Examples({
        "make new email:",
        "\tset author of email to \"Test\""
})
@Since("1.0")

public class ExprAuthorsOfEmail extends SimpleExpression<Address> {

    static {
        Skript.registerExpression(ExprAuthorsOfEmail.class, Address.class, ExpressionType.SIMPLE,
                "(author[s]|from[s] part) of %email%",
                "%email%'s (author[s]|from[s] part)"
        );
    }
    
    private Expression<Message> message;
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        message = (Expression<Message>) expr[0];
        return true;
    }
    
    @Override
    protected Address[] get( Event e ) {
        try {
            return message.getSingle(e).getFrom();
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }
    
    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Message email = message.getSingle(e);
        try {
            List<InternetAddress> addresses = new ArrayList<>();
            for (Object o1 : delta) {
                InternetAddress address = new InternetAddress((String) o1);
                address.validate();
                addresses.add(address);
            }
            
            switch (mode) {
                case SET:
                    email.setFrom(addresses.toArray(new InternetAddress[0])[0]);
                    break;
                
                case ADD:
                    email.addFrom(addresses.toArray(new InternetAddress[0]));
                    break;
                case REMOVE:
                    for (Object o : delta) {
                        InternetAddress a = new InternetAddress((String) o);
                        addresses.remove(a);
                    }
                    email.setFrom();
                    email.addFrom(addresses.toArray(new InternetAddress[0]));
                    break;
                case DELETE:
                    email.setFrom();
                    break;
                default:
                    break;
            }
        } catch (AddressException e1) {
            SkEmail.error("This email address is incorrect: " + delta[0]);
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
    }
    
    @Override
    public boolean isSingle() {
        return false;
    }
    
    @Override
    public Class<? extends Address> getReturnType() {
        return Address.class;
    }
    
    @Override
    public String toString( Event e, boolean debug ) {
        return null;
    }
    
}



