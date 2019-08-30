package com.olyno.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.olyno.skemail.scopes.ScopeEmailCreation;
import com.olyno.skemail.util.EffectSection;
import org.bukkit.event.Event;

import javax.mail.Message;

@Name("Email expression")
@Description("If it isn't inside an email scope, this expression returns a new Email. " +
        "If it is inside of an email scope, it returns the email that belongs to that scope.")
@Examples({
        "# outside a scope",
        "",
        "set {_e} to a new email",
        "",
        "# or in a scope",
        "",
        "make a new email:",
        "\tset object of email to \"TEST\"",
        "\tset body of email to \"Hey! That's a test!\"",
        "\tset receiver of email to \"hey@gmailcom\"",
        "\tadd \"plugins/myfile.txt\" to attachments of email",
        "set {_email} to last email"
})
@Since("1.0")

public class ExprEmail extends SimpleExpression<Message> {

    static {
        Skript.registerExpression(ExprEmail.class, Message.class, ExpressionType.SIMPLE,
                "[(the|an|[a] new|this|that)] [e]mail [(creator|build[er])]");
    }

    private boolean scope = false;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        scope = EffectSection.isCurrentSection(ScopeEmailCreation.class);
        return scope;
    }

    @Override
    protected Message[] get(Event e) {
        return new Message[]{
                scope ? ScopeEmailCreation.lastEmail : null
        };
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
        return "email";
    }
}

