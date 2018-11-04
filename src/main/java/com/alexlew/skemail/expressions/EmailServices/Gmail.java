package com.alexlew.skemail.expressions.EmailServices;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Name("Gmail service")
@Description("Returns the gmail service as EmailService.")
@Examples({
        "set {_service} to new gmail service"
})
@Since("1.3")

public class Gmail extends SimpleExpression<EmailService> {

    static {
        Skript.registerExpression(Gmail.class, EmailService.class, ExpressionType.SIMPLE,
                "[new] gmail [(service|session)]");
    }

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        return true;
    }

    @Override
    protected EmailService[] get( Event e ) {
        EmailService service = new EmailService();
        service.setName("gmail");
        service.setSmtp_address("smtp.gmail.com");
        service.setSmtp_port("465");
        service.setImap_address("imap.gmail.com");
        service.setImap_port("993");
        EmailService.lastEmailService = service;
        return new EmailService[] {service};
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
    public String toString( Event e, boolean debug ) {
        return "service";
    }

}