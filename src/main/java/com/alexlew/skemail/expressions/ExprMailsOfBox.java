package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;


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
