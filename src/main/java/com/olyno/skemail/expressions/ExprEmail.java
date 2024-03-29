package com.olyno.skemail.expressions;

import org.bukkit.event.Event;

import com.olyno.skemail.scopes.ScopeEmailCreation;

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
import jakarta.mail.Message;

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
                "[(the|an|1¦[a] new|this|that)] [e]mail [(creator|build[er])]");
    }

    private boolean isInScope = false;

    @Override
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult parseResult) {
        boolean isNew = parseResult.mark == 1;
        isInScope = getParser().isCurrentSection(ScopeEmailCreation.class);
        if (isNew && !isInScope) {
			Skript.error("You can only use the 'email' expression inside a 'create message' section");
			return false;
		}
        return true;
    }

    @Override
    protected Message[] get(Event e) {
        return new Message[]{
            isInScope ? ScopeEmailCreation.lastEmail : null
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

