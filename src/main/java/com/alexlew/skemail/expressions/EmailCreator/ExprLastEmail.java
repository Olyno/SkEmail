package com.alexlew.skemail.expressions.EmailCreator;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailCreator;
import com.alexlew.skemail.scopes.ScopeEmail;

@Name("Last Email")
@Description("Returns the email that was last made in a email scope")
@Examples({
        "set {_email} to the last email"
})
@Since("1.0")

public class ExprLastEmail extends SimpleExpression<EmailCreator> {

    static {
        Skript.registerExpression(ExprLastEmail.class, EmailCreator.class, ExpressionType.SIMPLE,
                "[the] last[ly] [(made|created|did)] [e]mail[[ ](creator|build[er])]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected EmailCreator[] get(Event e) {
        return new EmailCreator[]{ScopeEmail.lastEmailCreator};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EmailCreator> getReturnType() {
        return EmailCreator.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last made email";
    }

}