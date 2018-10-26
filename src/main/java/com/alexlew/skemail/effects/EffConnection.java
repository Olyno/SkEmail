package com.alexlew.skemail.effects;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Name("Connexion")
@Description("Connect to your email account. Current address available: hotmail, yahoo, gmail, live and outlook.")
@Examples({
        "login to \"email address\" with \"password of the email address\""
})
@Since("1.0")

public class EffConnection extends Effect {

    static {
        Skript.registerEffect(EffConnection.class,
                "(connexion|connection|login|connect) to [[sk]e]mail[s]");
    }

    public static String username;
    public static String password;

    public static String smtp_port;
    public static String smtp_address;
    public static String imap_port;
    public static String imap_address;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
        return true;
    }

    @Override
    protected void execute(Event e) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser
                    .parse(new FileReader("plugins/SkEmail/config.json"));
            JSONObject jsonObject = (JSONObject)object;

            username = (String) jsonObject.get("login");
            password = (String) jsonObject.get("password");
            smtp_address = (String) jsonObject.get("smtp_address");
            smtp_port = (String) jsonObject.get("smtp_port");
            imap_address = (String) jsonObject.get("imap_address");
            imap_port = (String) jsonObject.get("imap_port");

            System.out.println("[SkEmail] Mail account connected!");

        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            System.out.println("[SkEmail] Configuration's file not found.");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        /*username = user.getSingle(e);
        password = pass.getSingle(e);
        if (username.contains("@")) {
            String[] s_address1 = username.split("@");
            String[] s_address2 = s_address1[1].split("\\.");
            if (s_address2[0].equals("gmail")) {
                smtp_port = "465";
                smtp_address = "smtp.gmail.com";
                pop_port = "995";
                pop_address = "pop.gmail.com";
            } else if (s_address2[0].equals("hotmail") || s_address2[0].equals("live") || s_address2[0].equals("yahoo")) {
                smtp_port = "25";
                smtp_address = "smtp.live.com";
                pop_port = "995";
                pop_address = "pop-mail.outlook.com";
            } else if (s_address2[0].equals("outlook")) {
                smtp_port = "587";
                smtp_address = "smtp-mail.outlook.com";
                pop_port = "995";
                pop_address = "pop-mail.outlook.com";
            } else {
                Skript.error("[SkEmail] Wrong email address. An email address can't be " + username);
                bad = true;
            }

            if (bad == false) {
                System.out.print("[SkEmail] Connection established!");
            }


        } else {
            Skript.error("[SkEmail] Your connection's name must to be an address mail like myaddress@gmail.com and not " + username);
        }*/


    }

    @Override
    public String toString(Event e, boolean debug) {
        return "connection to mail " + username + " and password " + password;
    }
}
