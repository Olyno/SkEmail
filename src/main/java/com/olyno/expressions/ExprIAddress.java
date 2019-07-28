package com.olyno.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.olyno.SkEmail;
import com.olyno.types.IAddress;
import org.bukkit.event.Event;

import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
