package com.olyno.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.effects.EffConnection;
import com.olyno.expressions.ExprAttachFilesOfEmail;
import com.olyno.util.EffectSection;
import org.bukkit.event.Event;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

@Name("Scope Email Creation")
@Description("Scope for mail creation")
@Examples({
        "make new email:",
        "\tset author of email to \"myemail@gmail.com\"",
        "\tset receiver of email to \"receiver@gmail.com\"",
        "\tset object of email to \"Welcome\"",
        "\tset body of email to \"Welcome on my new server!\"",
        "\tset attach file of email to \"plugin/Test/myfile.txt\""
})
@Since("1.0")

public class ScopeEmailCreation extends EffectSection {

    public static Message lastEmail;

    static {
        Skript.registerCondition(ScopeEmailCreation.class,
                "(make|do|create) [new] [e]mail [(using|with|for) [account] (%-session%|%-string%)]");
    }

    private Expression<Object> connection;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition())
            return false;
        if (!hasSection()) {
            Skript.error("An email creation scope is useless without any content!");
            return false;
        }
        connection = (Expression<Object>) expr[0];
        loadSection(true);
        return true;
    }

    @Override
    public void execute(Event e) {
        Object c = connection == null ? null : connection.getSingle(e);
        Session session;
        if (c == null) {
            session = EffConnection.lastSession;
        } else if (c instanceof String) {
            session = EffConnection.accounts.get(c);
        } else {
            session = (Session) c;
        }
        lastEmail = new MimeMessage(session);
        EffConnection.lastSession = session;
        ExprAttachFilesOfEmail.files = new ArrayList<>();
        runSection(e);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make new email " + lastEmail + (connection != null ? " with " + connection.toString(e, debug) : "");
    }

}