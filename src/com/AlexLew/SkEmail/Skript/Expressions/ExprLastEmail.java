package com.AlexLew.SkEmail.Skript.Expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import com.AlexLew.SkEmail.Skript.Types.EmailBuilder;
import com.AlexLew.SkEmail.Skript.Utils.Scopes.ScopeEmail;

@Name("Last Email")
@Description("Return last email builder result")
@Examples({
			"send last email"
	})
@Since("1.0")

public class ExprLastEmail extends SimpleExpression<EmailBuilder> {

    static {
    	Skript.registerExpression(ExprLastEmail.class, EmailBuilder.class, ExpressionType.SIMPLE, "[the] last[ly] [(made|created)] email[[ ]builder]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected EmailBuilder[] get(Event e) {
        return new EmailBuilder[]{ScopeEmail.lastEmail};
    }

    @Override
    public Class<? extends EmailBuilder> getReturnType() {
        return EmailBuilder.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last made email";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}