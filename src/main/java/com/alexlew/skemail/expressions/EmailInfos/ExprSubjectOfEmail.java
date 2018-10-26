package com.alexlew.skemail.expressions.EmailInfos;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.skemail.expressions.EmailCreator.ExprBodyOfEmail;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

@Name("Subject of Email")
@Description("Returns the subject of an email.")
@Examples({
        "set {_author} to subject of last email read"
})
@Since("1.2")

public class ExprSubjectOfEmail extends SimplePropertyExpression<Message, String> {

    static {
        register(ExprSubjectOfEmail.class, String.class,
                "(title|subject|object)[s]", "emailreader");
    }

    @Override
    public String convert( Message email ) {
        try {
            if (email.getSubject() != null) {
                return email.getSubject();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
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