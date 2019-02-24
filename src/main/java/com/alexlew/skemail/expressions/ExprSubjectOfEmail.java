package com.alexlew.skemail.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import javax.mail.Message;
import javax.mail.MessagingException;

@Name("Object of Email")
@Description("Returns the object of an email. Can be set in a email scope")
@Examples({
        "make new email:",
        "\tset object of email to \"Test\""
})
@Since("1.0")

public class ExprSubjectOfEmail extends SimplePropertyExpression<Message, String> {

    static {
        register(ExprSubjectOfEmail.class, String.class,
                "(title|subject|object)", "email");
    }

    @Override
    public String convert(Message email) {
        try {
            return email.getSubject();
        } catch (MessagingException e) {
            return null;
        }
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        try {
            for (Message email : getExpr().getArray(e)) {
                switch (mode) {
                    case SET:
                        email.setSubject((String) delta[0]);
                        break;
                    case DELETE:
                        email.setSubject(null);
                        break;
                    default:
                        break;
                }
            }
            
        } catch (MessagingException ignored) { }
    }

    @Override
    protected String getPropertyName() {
        return "subject";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}



