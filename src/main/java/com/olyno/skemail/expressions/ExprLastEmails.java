package com.olyno.skemail.expressions;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.Event;

import com.olyno.skemail.SkEmail;
import com.olyno.skemail.effects.EffConnection;
import com.olyno.skemail.events.javaxmail.MailFolder;

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
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.URLName;

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
                "last[ly] [%-integer%] [e]mail[s] [in [(folder|dir)] (%-string%|%-folder%)] [(using|with) (%-session%|%-string%)]",
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
            end = (Expression<Integer>) expr[0];
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
        Integer endN = end != null ? end.getSingle(e) : -1;

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

            if (endN == -1) {
                Message[] messages = inbox.getMessages();
                ArrayUtils.reverse(messages);
                return messages;
            }

            Message[] messages = new Message[endN - startN];
            for (int i = startN; i < endN; i++) {
                messages[i] = inbox.getMessage(inbox.getMessageCount() - i);
            }
            return messages;

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
