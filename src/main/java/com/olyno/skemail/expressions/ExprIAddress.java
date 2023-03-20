package com.olyno.skemail.expressions;

import org.bukkit.event.Event;

import com.olyno.skemail.SkEmail;
import com.olyno.skemail.types.IAddress;

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
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

@Name("IAddress")
@Description("Returns a internet address from a string.")
@Examples({
        ""
})
@Since("1.5")

public class ExprIAddress extends SimpleExpression<IAddress> {

    static {
        Skript.registerExpression(ExprIAddress.class, IAddress.class, ExpressionType.SIMPLE,
                "%string% as %recipienttype%"
        );
    }

    private Expression<String> address;
    private Expression<Message.RecipientType> recipient;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        address = (Expression<String>) expr[0];
        recipient = (Expression<Message.RecipientType>) expr[1];
        return true;
    }

    @Override
    protected IAddress[] get(Event e) {
        String addr = address.getSingle(e);
        Message.RecipientType type = recipient.getSingle(e);
        try {
            InternetAddress internetAddress = new InternetAddress(addr);
            return new IAddress[]{new IAddress(internetAddress, type)};
        } catch (AddressException e1) {
            SkEmail.error("This email address is not valid: " + addr);
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends IAddress> getReturnType() {
        return IAddress.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return address.toString(e, debug) + " as " + recipient.toString(e, debug);
    }
}
