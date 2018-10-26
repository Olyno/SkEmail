package com.alexlew.skemail.expressions.EmailInfos;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

@Name("Body of Email")
@Description("Returns the body of an email.")
@Examples({
        "set {_author} to body of last email read"
})
@Since("1.2")

public class ExprBodyOfEmail extends SimplePropertyExpression<Message, String> {

    static {
        register(ExprBodyOfEmail.class, String.class,
                "(body|content)", "emailreader");
    }

    @Override
    public String convert( Message email ) {
        try {
            if (email.getContent() != null) {
                return (String) email.getContent();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return "body";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}