package com.alexlew.skemail.expressions;

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
import com.alexlew.skemail.effects.EffConnection;
import com.alexlew.skemail.types.EmailConnection;
import org.bukkit.event.Event;

@Name("Last Email")
@Description("Returns the email that was last made in a email scope")
@Examples({
        "set {_email} to the last email"
})
@Since("1.0")

public class ExprLastConnection extends SimpleExpression<EmailConnection> {

    static {
        Skript.registerExpression(ExprLastConnection.class, EmailConnection.class, ExpressionType.SIMPLE,
                "[the] last[ly] [(account|session)] connect[ion]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected EmailConnection[] get( Event e) {
        return new EmailConnection[]{EffConnection.lastEmailConnection};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EmailConnection> getReturnType() {
        return EmailConnection.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last connection";
    }

}