package com.olyno.skemail.effects;

import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;

import com.olyno.skemail.SkEmail;
import com.olyno.skemail.events.javaxmail.MailConnection;
import com.olyno.skemail.types.EmailService;
import com.olyno.skemail.util.AsyncEffect;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;

@Name("Connection")
@Description("Connect to your email account.")
@Examples({
        "login to gmail session \"email address of connection\" using pass \"password of the email address\""
})
@Since("1.0")

public class EffConnection extends AsyncEffect {

    public static HashMap<String, Session> accounts = new HashMap<>();
    public static Session lastSession;

    static {
        registerAsyncEffect(EffConnection.class,
                "(login|connect) to %emailservice% [(account|session)] [(with|as|from) (address|user[name]|[e]mail)] %string% (and|using) [with] [pass[word]] %string% [as %-string%] [and store [it] in %-objects%]");
    }

    private Expression<EmailService> service;
    private Expression<String> user;
    private Expression<String> pass;
    private Expression<String> id;
    private Variable<?> varExpr;

    @SuppressWarnings("unchecked")
    @Override
    public boolean initAsync(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        service = (Expression<EmailService>) expr[0];
        user = (Expression<String>) expr[1];
        pass = (Expression<String>) expr[2];
        if (expr[3] != null) {
            id = (Expression<String>) expr[3];
        }
        if (expr[4] != null) {
            if (!(expr[4] instanceof Variable<?>)) {
                SkEmail.error("You can register the connection in a var, and only in a var, not " + expr[4].toString());
                return false;
            }
        }

        varExpr = (Variable<?>) expr[4];
        return true;
    }

    @Override
    protected void executeAsync(Event e) {
        EmailService serviceType = service.getSingle(e);
        String username = id != null ? id.getSingle(e) : user.getSingle(e);
        String password = pass.getSingle(e);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            SkEmail.error("Your username and password can't be empty when you use connect's effect");
        }

        if (!accounts.containsKey(username)) {
            try {
                Properties props = new Properties();

                // SMTP
                props.put("mail.smtp.host", serviceType.getSmtp_address());
                props.put("mail.smtp.port", Integer.parseInt(serviceType.getSmtp_port()));
                props.put("mail.smtp.socketFactory.port", Integer.parseInt(serviceType.getSmtp_port()));
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                // IMAP
                props.put("mail.imap.host", serviceType.getImap_address());
                props.put("mail.imap.port", Integer.parseInt(serviceType.getImap_port()));
                props.put("mail.imap.socketFactory.port", Integer.parseInt(serviceType.getImap_port()));
                props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.imap.auth", "true");
                props.put("mail.imap.starttls.enable", "true");

                Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                Transport transport = session.getTransport("smtp");
                if (!transport.isConnected()) {
                    transport.addConnectionListener(new MailConnection());
                    transport.connect(
                            serviceType.getSmtp_address(),
                            Integer.parseInt(serviceType.getSmtp_port()),
                            username,
                            password
                    );

                    transport.close();
                    accounts.remove(username);
                    accounts.put(username, session);
                    lastSession = session;
                    if (varExpr != null) {
                        varExpr.change(e, new Object[]{session}, Changer.ChangeMode.SET);
                    }
                }

            } catch (AuthenticationFailedException e1) {
                SkEmail.error("You used a wrong mail address or password. Please check them. Else try to see if \"Less secure app access\" is turned on.");
            } catch (MessagingException e2) {
                SkEmail.error("Impossible to connect to your mail box. Try to restart your server.");
            }

        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "connection to mail " + user.toString(e, debug) + " and password " + pass.toString(e, debug);
    }
}
