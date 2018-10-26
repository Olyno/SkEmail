package com.alexlew.skemail.expressions.EmailInfos;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.effects.EffConnection;
import org.bukkit.event.Event;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@Name("Mails of Box")
@Description("Returns mails of a specific box.")
@Examples({
        "set {_mails::*} to last 5 mails"
})
@Since("1.2")

public class ExprMailsOfBox extends SimpleExpression<Message> {

    static {
        Skript.registerExpression(ExprMailsOfBox.class, Message.class, ExpressionType.COMBINED,
                "[the] last[ly] [%-integer%] [e]mails [(in|from) [the] [box] [(named|with name)] %-string%]");
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
    protected Message[] get( Event e ) {
        String box_name = box == null ? "INBOX" : box.getSingle(e);
        Integer nbr_times = box == null ? 0 : nbr.getSingle(e);

        try {
            Properties properties = new Properties();
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");

            store.connect(EffConnection.imap_address, EffConnection.username, EffConnection.password);

            Folder emailFolder = store.getFolder(box_name);
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            List<Message> mailsList = new ArrayList<Message>(Arrays.asList(messages));

            if (nbr_times < 0 || nbr_times == 0) {
                nbr_times = messages.length;
            }

            for (int i = 0; i < nbr_times; i++) {
                 mailsList.add(messages[i]);
                 if(nbr_times - 1 == i) {
                      ExprLastEmailRead.lastEmailRead = messages[i];
                  }
            }

            Message[] mailsReader = mailsList.toArray(new Message[0]);
            //emailFolder.close(false);
            //store.close();
            return mailsReader;

        } catch (NoSuchProviderException e1) {
            System.out.println("WAIT1");
            e1.printStackTrace();
        } catch (MessagingException e1) {
            System.out.println("WAIT2");
            e1.printStackTrace();
        } catch (Exception e1) {
            System.out.println("WAIT3");
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
    public String toString( Event e, boolean debug ) {
        return "lastly " + nbr.toString(e, debug) + " in box " + box.toString(e, debug);
    }
}
