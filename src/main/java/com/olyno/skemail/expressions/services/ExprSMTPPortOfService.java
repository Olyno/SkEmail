package com.olyno.skemail.expressions.services;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.skemail.types.EmailService;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;

@Name("SMTP Port of Email Service")
@Description("Returns the smtp port of an email service. Can be set in a email service scope")
@Examples({
        "make new email service:",
        "\tset smtp port of service to 900"
})
@Since("1.3")

public class ExprSMTPPortOfService extends SimplePropertyExpression<EmailService, Object> {

    static {
        register(ExprSMTPPortOfService.class, Object.class,
                "smtp(-|_| )port", "emailservice");
    }

    @Override
    public Object convert(EmailService service) {
        return service.getSmtp_port();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.DELETE) {
            return new Class[]{Object.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        if (delta[0] instanceof String || (delta[0] instanceof Integer && StringUtils.isNumeric((String) delta[0]))) {
            for (EmailService service : getExpr().getArray(e)) {
                switch (mode) {
                    case SET:
                        service.setSmtp_port(delta[0]);
                        break;
                    case DELETE:
                        service.setSmtp_port(null);
                        break;
                    default:
                        break;
                }
            }
        } else {
            System.out.println("[SkEmail] A smtp port must to be an integer or a string like \"900\" or 900, and not " + delta[0]);
        }

    }

    @Override
    protected String getPropertyName() {
        return "smtp address";
    }

    @Override
    public Class<? extends Object> getReturnType() {
        return Object.class;
    }
}



