package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilderbase;
import com.alexlew.skemail.scopes.ScopeEmail;

@Name("Last Email")
@Description("Returns the email that was last made in a email scope")
@Examples({
			"set {_email} to the last email"
	})
@Since("1.0")

public class ExprLastEmail extends SimpleExpression<EmailBuilderbase> {

    static {
    	Skript.registerExpression(ExprLastEmail.class, EmailBuilderbase.class, ExpressionType.SIMPLE, 
    			"[the] last[ly] [(made|created|did)] email[[ ]builder]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected EmailBuilderbase[] get(Event e) {
        return new EmailBuilderbase[]{ScopeEmail.lastEmail};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EmailBuilderbase> getReturnType() {
        return EmailBuilderbase.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last made email";
    }

}