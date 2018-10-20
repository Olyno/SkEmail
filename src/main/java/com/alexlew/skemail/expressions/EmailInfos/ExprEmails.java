package com.alexlew.skemail.expressions.EmailInfos;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class ExprEmails extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprEmails.class, String.class, ExpressionType.SIMPLE,
                "[the] [last[ly] %-integer%] emails (in|from) [the] [box] [(named|with name)] %string%");
    }

    private Expression<Integer> nbr;
    private Expression<String> box;

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        nbr = (Expression<Integer>) expr[0];
        box = (Expression<String>) expr[1];
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
