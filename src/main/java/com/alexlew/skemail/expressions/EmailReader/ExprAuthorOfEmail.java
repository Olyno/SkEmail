package com.alexlew.skemail.expressions.EmailReader;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Name("Author of Email")
@Description("Returns the author of an email.")
@Examples({
        "set {_authors::*} to authors of last email read"
})
@Since("1.2")

public class ExprAuthorOfEmail extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprAuthorOfEmail.class, String.class, ExpressionType.SIMPLE,
                "%emailreader%['s] (author[s]|from part)",
                "(from part|author[s]) of %emailreader%");
    }

    private Expression<Message> email;

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        email = (Expression<javax.mail.Message>) expr[0];
        return true;
    }

    @Override
    protected String[] get( Event e ) {
        Message email = this.email.getSingle(e);
        if (email == null) {
            return null;
        }
        List<String> fromPart = new ArrayList<String>();
        Address[] authors = new Address[0];
        try {
            authors = email.getFrom();
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
        if (authors.length > 0) {
            for (Address address : authors) {
                fromPart.add(address.toString());
            }
        }
        return fromPart.toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "receivers " + email.toString(e, debug);
    }

}