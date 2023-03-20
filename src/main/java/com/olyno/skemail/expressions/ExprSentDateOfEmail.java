package com.olyno.skemail.expressions;

import java.util.Date;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;

@Name("Sent Date of Email")
@Description("Returns the sent date of an email. Can be set in a email scope")
@Examples({
        "make new email:",
        "\tset sent date of email to \"welcome on my new server!\""
})
@Since("1.5")

public class ExprSentDateOfEmail extends SimplePropertyExpression<Message, Date> {

    static {
        register(ExprSentDateOfEmail.class, Date.class,
                "sent date", "email");
    }

    @Override
    public Date convert(Message email) {
        try {
            return email.getSentDate();
        } catch (MessagingException e) {
            e.printStackTrace();
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
                        email.setSentDate((Date) delta[0]);
                    case DELETE:
                        email.setSentDate(null);
                    default:
                        break;
                }
            }
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected String getPropertyName() {
        return "sent date";
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }
}



