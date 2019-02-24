package com.alexlew.skemail.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.types.EmailService;
import org.bukkit.event.Event;

import com.alexlew.skemail.util.EffectSection;

@Name("Scope Email Service")
@Description("Scope for mail service")
@Examples({
        "make new email service:",
        "\tset name of mail service to \"my service\"",
        "\tset  smtp address of mail service to \"smtp.myservice.com\"",
        "\tset smtp port of mail service to 465",
        "\tset imap address of mail service to \"imap.myservice.com\"",
        "\tset imap port of mail service to 900",
        "set {_myservice} to last mail service"
})
@Since("1.3")

public class ScopeEmailService extends EffectSection {

    public static EmailService lastEmailService;

    static {
        Skript.registerCondition(ScopeEmailService.class,
                "(make|do|create) [new] [e]mail service");
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition())
            return false;
        if (!hasSection()) {
            Skript.error("An email service scope is useless without any content!");
            return false;
        }
        loadSection(true);
        return true;
    }

    @Override
    public void execute(Event e) {
        lastEmailService = new EmailService();
        runSection(e);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make new email service " + lastEmailService.toString();
    }

}