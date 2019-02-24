package com.alexlew.skemail.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.SkEmail;
import com.alexlew.skemail.events.javaxmail.MailConnection;
import com.alexlew.skemail.events.javaxmail.MailTransport;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

import javax.mail.*;
import java.util.HashMap;
import java.util.Properties;

@Name("MailConnection")
@Description("Connect to your email account.")
@Examples({
        "login to gmail session \"email address of connection\" using pass \"password of the email address\""
})
@Since("1.0")

public class EffConnection extends Effect {

    static {
        Skript.registerEffect(EffConnection.class,
                "(login|connect) to %emailservice% [(account|session)] [(with|as|from) (address|user[name]|[e]mail)] %string% (and|using) [with] [pass[word]] %string% [and store [it] in %-objects%]");
    }

    private Expression<EmailService> service;
    private Expression<String> user;
    private Expression<String> pass;
    private Variable<?> varExpr;
    private VariableString varName;
    
    public static HashMap<String, Session> accounts = new HashMap<>();
    public static Session lastConnection;
    public static Session lastSession;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        service = (Expression<EmailService>) expr[0];
        user = (Expression<String>) expr[1];
        pass = (Expression<String>) expr[2];
        if (expr[3] != null) {
            if (!(expr[3] instanceof Variable<?>)) {
                System.out.println("[SkEmail] You can register the connection in a var, and only in a var, not " + expr[3].toString());
                return false;
            }
        }

        varExpr = (Variable<?>) expr[3];
        return true;
    }

    @Override
    protected void execute(Event e) {
        EmailService serviceType = service.getSingle(e);
        int port = Integer.parseInt(serviceType.getSmtp_port());
        String host = serviceType.getSmtp_address();
        String login = user.getSingle(e);
        String pwd = pass.getSingle(e);
    
        if (!accounts.containsKey(login)) {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.socketFactory.port", port);
                props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", port);
                Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(login, pwd);
                            }
                });
                Transport transport = session.getTransport("smtp");
                transport.addConnectionListener(new MailConnection());
                transport.addTransportListener(new MailTransport());
                transport.connect(host, port, login, pwd);
        
                // If success
                accounts.remove(login);
                accounts.put(login, session);
                lastConnection = session;
                lastSession = session;
                if (varExpr != null) {
                    varExpr.change(e, new Object[] {session}, Changer.ChangeMode.SET);
                }
        
            } catch (AuthenticationFailedException e1) {
                SkEmail.error("You used a wrong mail address or password. Please check them.");
        
            } catch (MessagingException e2) {
                SkEmail.error("Impossible to connect to your mail box. Try to restart your server.");
            }
        }
        
        
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "MailConnection to mail " + user.getSingle(e) + " and password " + pass.getSingle(e);
    }
}
