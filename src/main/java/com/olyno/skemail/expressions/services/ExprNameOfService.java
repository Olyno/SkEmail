package com.olyno.skemail.expressions.services;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.skemail.types.EmailService;
import org.bukkit.event.Event;

@Name("Name of Email Service")
@Description("Returns the name of an email service. Can be set in a email service scope")
@Examples({
        "make new email service:",
        "\tset name of service to \"My service\""
})
@Since("1.3")

public class ExprNameOfService extends SimplePropertyExpression<EmailService, String> {

    static {
        register(ExprNameOfService.class, String.class,
                "service name", "emailservice");
    }

    @Override
    public String convert(EmailService service) {
        return service.getName();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        for (EmailService service : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    service.setName((String) delta[0]);
                    break;
                case DELETE:
                    service.setName(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "service name";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}



