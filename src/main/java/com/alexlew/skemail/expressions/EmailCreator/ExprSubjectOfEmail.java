package com.alexlew.skemail.expressions.EmailCreator;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailCreator;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

@Name("Object of Email")
@Description("Returns the object of an email. Can be set in a email scope")
@Examples({
        "make new email:",
        "\tset object of email to \"Test\""
})
@Since("1.0")

public class ExprSubjectOfEmail extends SimplePropertyExpression<EmailCreator, String> {

    static {
        register(ExprSubjectOfEmail.class, String.class,
                "(title|subject|object)[s]", "emailcreator");
    }

    @Override
    public String convert(EmailCreator email) {
        return email.getObject();
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
        for (EmailCreator email : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    email.setObject((String) delta[0]);
                    break;
                case DELETE:
                    email.setObject(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "object";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}



