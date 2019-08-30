package com.olyno.skemail.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.olyno.skemail.SkEmail;
import com.olyno.skemail.events.javaxmail.MailTransport;
import com.olyno.skemail.util.AsyncEffect;
import org.bukkit.event.Event;

import javax.mail.*;
import javax.mail.internet.InternetAddress;

@Name("Send Email")
@Description("Send an email")
@Examples({
        "command send:" +
                "\ttrigger:" +
                "\t\tmake new email:" +
                "\t\t\tset object of email to \"Look this email, it's awesome!\"" +
                "\t\t\tset body of email to \"Hey dude, %nl% I got a cat, what name can I give him?\"" +
                "\t\t\tset receiver of email to \"mybestfriend@gmailcom\"" +
                "\t\tsend last email created"
})
@Since("1.0")

public class EffSendEmail extends AsyncEffect {

    public static Message lastEmailSent;

    static {
        Skript.registerEffect(EffSendEmail.class,
                "send %email% [to %-string%] [(using|with) (%-session%|%-string%)]");
    }

    private Expression<Message> email;
    private Expression<String> rec;
    private Expression<Object> connection;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        email = (Expression<Message>) expr[0];
        if (expr[1] != null) {
            rec = (Expression<String>) expr[1];
        }
        if (expr[2] != null) {
            connection = (Expression<Object>) expr[2];
        }
        return true;
    }

    @Override
    protected void execute(Event e) {
        Message emailObject = email.getSingle(e);

        executeCode(e, () -> {
            try {
                Session session = null;
                Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

                if (connection != null) {
                    Object co = connection.getSingle(e);
                    if (co instanceof String) {
                        if (EffConnection.accounts.containsKey(co)) {
                            session = EffConnection.accounts.get(co);
                        } else {
                            SkEmail.error("This account is not connected: " + co);
                        }
                    } else if (co instanceof Session) {
                        session = (Session) co;
                    } else if (EffConnection.lastSession != null) {
                        session = EffConnection.lastSession;
                    } else {
                        SkEmail.error("You must to be login to a mail account before send an email.");
                        return;
                    }
                } else if (EffConnection.lastSession != null) {
                    session = EffConnection.lastSession;
                } else {
                    SkEmail.error("You must to be login to a mail account before send an email.");
                    return;
                }

                Address[] addresses = rec != null ?
                        new Address[]{new InternetAddress(rec.getSingle(e))}
                        : emailObject.getAllRecipients();

                Transport transport = session.getTransport("smtp");
                transport.addTransportListener(new MailTransport());
                URLName urlname = transport.getURLName();
                transport.connect(
                        urlname.getHost(),
                        urlname.getPort(),
                        urlname.getUsername(),
                        urlname.getPassword()
                );

                transport.sendMessage(emailObject, addresses);
                transport.close();

                lastEmailSent = emailObject;

            } catch (MessagingException e1) {
                SkEmail.error("An error occurred. Try to check this link and retry: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail");
                SkEmail.error("If the problem persists, try to reload your server.");
                e1.printStackTrace();
            } catch (IllegalStateException e1) {
                SkEmail.error("Impossible to connect to your mail box to send your email. Try to restart your server.");
            }
        });

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send email " + email.toString(e, debug);
    }

}
