package com.alexlew.skemail.expressions.EmailReader;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.mail.Message;

@Name("Last Email")
@Description("Returns the email that was last read.")
@Examples({
        "set {_email} to the last email read"
})
@Since("1.2")

public class ExprLastEmailRead extends SimpleExpression<Message> {

    static {
        Skript.registerExpression(ExprLastEmailRead.class, Message.class, ExpressionType.SIMPLE,
                "[the] last[ly] [e]mail read");
    }

    public static Message lastEmailRead;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected Message[] get(Event e) {
        return new Message[]{lastEmailRead};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last read email";
    }

}