package com.alexlew.skemail.effects;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import com.alexlew.skemail.types.EmailConnection;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Connection")
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

    public static EmailConnection lastEmailConnection;

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
        EmailConnection thisConnection = new EmailConnection();
        thisConnection.setUsername(user.getSingle(e));
        thisConnection.setPassword(pass.getSingle(e));
        thisConnection.setService(service.getSingle(e));
        lastEmailConnection = thisConnection;
        if (varExpr != null) {
            varExpr.change(e, new EmailConnection[] { thisConnection }, Changer.ChangeMode.SET);
        }


    }

    @Override
    public String toString(Event e, boolean debug) {
        return "connection to mail " + user.getSingle(e) + " and password " + pass.getSingle(e);
    }
}
