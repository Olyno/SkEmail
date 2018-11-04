package com.alexlew.skemail.expressions.EmailServices.custom;

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
import com.alexlew.skemail.scopes.ScopeEmailCreator;
import com.alexlew.skemail.scopes.ScopeEmailService;
import com.alexlew.skemail.types.EmailCreator;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

@Name("Last Email Service")
@Description("Returns the email service that was last made in a email service scope")
@Examples({
        "set {_service} to the last email service"
})
@Since("1.3")

public class ExprLastEmailService extends SimpleExpression<EmailService> {

    static {
        Skript.registerExpression(ExprLastEmailService.class, EmailService.class, ExpressionType.SIMPLE,
                "[the] last[ly] [e]mail service");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        return true;
    }

    @Override
    protected EmailService[] get( Event e) {
        return new EmailService[]{ScopeEmailService.lastEmailService};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EmailService> getReturnType() {
        return EmailService.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the last made email service";
    }

}