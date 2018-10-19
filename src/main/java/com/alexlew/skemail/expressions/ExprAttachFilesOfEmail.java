package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.types.EmailBuilderbase;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Name("Attach files of Email")
@Description("Returns attach files of an email. Can be set in a email scope")
@Examples({
        "make new email:",
        "\tset attachement of email to \"plugin/myfile.txt\""
})
@Since("1.1")

public class ExprAttachFilesOfEmail extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprAttachFilesOfEmail.class, String.class, ExpressionType.COMBINED,
                "[the] %emailbuilderbase%['s] (attached file|attachment)[s]",
                          "(attached file|attachment)[s] of %emailbuilderbase%");
    }

    private Expression<EmailBuilderbase> email;

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        email = (Expression<EmailBuilderbase>) expr[0];
        return true;
    }

    @Override
    protected String[] get( Event e ) {
        String[] attachments = email.getSingle(e).getAttachments();
        System.out.println("XX: " + Arrays.toString(attachments));
        return new String[] {Arrays.deepToString(email.getSingle(e).getAttachments())};
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (EmailBuilderbase email : email.getArray(e)) {
            switch (mode) {
                case SET:
                    email.setAttachment((String) delta[0]);
                    break;
                case DELETE:
                    email.setAttachment(null);
                    break;
                case ADD:
                    email.addAttachment((String) delta[0]);
                    break;
                case REMOVE:
                    email.removeAttachment((String) delta[0]);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "attach file " + email.toString(e, debug);
    }

}
