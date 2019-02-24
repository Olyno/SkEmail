package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import com.alexlew.skemail.scopes.ScopeEmailCreation;

import javax.mail.Message;

@Name("Last Email")
@Description("Returns the email that was last made in a email scope")
@Examples({
        "set {_email} to the last email"
})
@Since("1.0")

public class ExprLastEmail extends SimpleExpression<Message> {

    static {
        Skript.registerExpression(ExprLastEmail.class, Message.class, ExpressionType.SIMPLE,
                "[the] last[ly] [e]mail (made|created|did)");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected Message[] get(Event e) {
        return new Message[]{ScopeEmailCreation.lastEmail};
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
        return "the last email made";
    }

}