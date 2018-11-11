package com.alexlew.skemail.expressions.EmailCreator;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.skemail.types.EmailCreator;
import org.bukkit.event.Event;

@Name("Author/From part of Email")
@Description("Returns the author/from part of an email. Can be set in a email scope")
@Examples({
        "make new email:",
        "\tset author of email to \"Test\""
})
@Since("1.0")

public class ExprAuthorOfEmail extends SimplePropertyExpression<EmailCreator, String> {

    static {
        register(ExprAuthorOfEmail.class, String.class,
                "(author|from part)", "emailcreator");
    }

    @Override
    public String convert(EmailCreator email) {
        return email.getAuthor();
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
                    email.setAuthor((String) delta[0]);
                    break;
                case DELETE:
                    email.setAuthor(null);
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



