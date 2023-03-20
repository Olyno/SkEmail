package com.olyno.skemail.expressions;

import org.bukkit.event.Event;

import com.olyno.skemail.effects.EffSendEmail;

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
import jakarta.mail.Message;

@Name("Last Email Sent")
@Description("Returns the email that was last sent.")
@Examples({
        "set {_email} to the last email sent"
})
@Since("1.5")

public class ExprLastEmailSent extends SimpleExpression<Message> {

    static {
        Skript.registerExpression(ExprLastEmailSent.class, Message.class, ExpressionType.SIMPLE,
                "[the] last[ly] [e]mail (sent|transported)");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected Message[] get(Event e) {
        return new Message[]{EffSendEmail.lastEmailSent};
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
        return "last email sent";
    }

}