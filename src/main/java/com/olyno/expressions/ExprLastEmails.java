package com.olyno.expressions;

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
import com.olyno.SkEmail;
import com.olyno.effects.EffConnection;
import com.olyno.events.javaxmail.MailFolder;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.Event;

import javax.mail.*;

@Name("Last Emails")
@Description("Return last received mails in a box.")
@Examples({
        "command read:\n" +
                "\ttrigger:\n" +
                "\t\tset {_mails::*} to last 2 mails\n" +
                "\t\tbroadcast \"The author of the last received mail is %author of {_mails::1}%\""
})
@Since("1.5")

public class ExprLastEmails extends SimpleExpression<Message> {

    static {
        Skript.registerExpression(ExprLastEmails.class, Message.class, ExpressionType.SIMPLE,
                "last[ly] %integer% [e]mail[s] [in [(folder|dir)] (%-string%|%-folder%)] [(using|with) (%-session%|%-string%)]",
                "[e]mail[s] from %integer% to %integer% [in [(folder|dir)] (%-string%|%-folder%)] [(using|with) (%-session%|%-string%)]"
        );
    }

    private Expression<Integer> start;
    private Expression<Integer> end;
    private Expression<Object> dir;
    private Expression<Object> connection;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (matchedPattern == 0) {
            start = (Expression<Integer>) expr[0];
            dir = (Expression<Object>) expr[1];
            connection = (Expression<Object>) expr[2];
        } else {
            start = (Expression<Integer>) expr[0];
            end = (Expression<Integer>) expr[1];
            dir = (Expression<Object>) expr[2];
            connection = (Expression<Object>) expr[3];
        }

        return true;
    }

    @Override
    protected Message[] get(Event e) {
        Session session = EffConnection.lastSession;
        String folderName = "INBOX";
        Integer startN = start != null ? start.getSingle(e) : 0;

        // Define the folder name if not null
        if (dir != null) {
            if (dir.getSingle(e) instanceof String) {
                folderName = (String) dir.getSingle(e);
            } else if (dir.getSingle(e) instanceof Folder) {
                folderName = ((Folder) dir.getSingle(e)).getName();
            }
        }

        // Define the session if not null
        if (connection != null) {
            if (connection.getSingle(e) instanceof String) {
                if (EffConnection.accounts.containsKey(connection.getSingle(e))) {
                    session = EffConnection.accounts.get(connection.getSingle(e));
                } else {
                    SkEmail.error("No account exists with this name: " + connection.getSingle(e));
                }
            } else if (connection.getSingle(e) instanceof Session) {
                session = (Session) connection.getSingle(e);
            } else {
                SkEmail.error("Connection type error. It's not a Session or String type: " + connection.getSingle(e).getClass().getName());
            }
        }


        try {
            Store store = session.getStore("imap");
            if (!store.isConnected()) {
                store.addFolderListener(new MailFolder());
                URLName urlname = store.getURLName();
                store.connect(
                        urlname.getHost(),
                        urlname.getPort(),
                        urlname.getUsername(),
                        urlname.getPassword()
                );
            }

            Folder inbox = store.getFolder(folderName);
            inbox.open(Folder.READ_ONLY);

            if (end != null) {
                if (end.getSingle(e) > 0 && end.getSingle(e) > startN) {
                    Message[] messages = inbox.getMessages(startN, end.getSingle(e));
                    ArrayUtils.reverse(messages);
                    return messages;
                } else {
                    SkEmail.error("You can only retrieve the last emails in a given order, and can't be equal to 0. The end cannot be before the beginning (" + end.getSingle(e) + " = 0 or > " + startN + ")");
                }
            } else if (startN > 0) {
                Message[] messages = new Message[startN];
                for (int i = 0; i < startN; i++) {
                    messages[i] = inbox.getMessages()[i];
                }
                ArrayUtils.reverse(messages);
                return messages;
            } else {
                Message[] messages = inbox.getMessages();
                ArrayUtils.reverse(messages);
                return messages;
            }

            return null;

        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last emails";
    }
}
