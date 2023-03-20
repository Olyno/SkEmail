package com.olyno.skemail.expressions;

import org.bukkit.event.Event;

import com.olyno.skemail.effects.EffConnection;

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
import jakarta.mail.Session;

@Name("Last Connection")
@Description("Returns the last account connected.")
@Examples({
        "set {_last session} to the last session"
})
@Since("1.5")

public class ExprLastConnection extends SimpleExpression<Session> {

    static {
        Skript.registerExpression(ExprLastConnection.class, Session.class, ExpressionType.SIMPLE,
                "[the] last[ly] [connected] (account|session)");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected Session[] get(Event e) {
        return new Session[]{EffConnection.lastSession};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Session> getReturnType() {
        return Session.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last connection";
    }

}