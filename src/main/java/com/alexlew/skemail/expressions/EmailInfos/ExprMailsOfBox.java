package com.alexlew.skemail.expressions.EmailInfos;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.effects.EffConnection;
import org.bukkit.event.Event;

import javax.mail.*;
import java.util.Properties;


public class ExprMailsOfBox extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprMailsOfBox.class, String.class, ExpressionType.COMBINED,
                "last[ly] %integer% [e]mails (from|in) [box] %string%");
    }

    private Expression<String> box;
    private Expression<Integer> nbr;

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        box = (Expression<String>) expr[0];
        nbr = (Expression<Integer>) expr[1];
        return true;
    }

    @Override
    protected String[] get( Event e ) {
        String box_name = box.getSingle(e);
        try {
            Properties properties = new Properties();

            properties.put("mail.pop3.host", EffConnection.pop_address);
            properties.put("mail.pop3.port", EffConnection.pop_port);
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore("pop3s");

            store.connect(EffConnection.pop_address, EffConnection.username, EffConnection.password);

            Folder emailFolder = store.getFolder(box_name);
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e1) {
            e1.printStackTrace();
        } catch (MessagingException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return new String[0];
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
        return "box " + box.toString(e, debug);
    }
}
