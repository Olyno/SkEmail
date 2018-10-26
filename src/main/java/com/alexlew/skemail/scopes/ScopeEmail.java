package com.alexlew.skemail.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailCreator;
import com.alexlew.skemail.util.EffectSection;

@Name("Scope Email")
@Description("Scope for mail")
@Examples({
		"make new email:",
		"\tset author of email to \"myemail@gmail.com\"",
		"\tset receiver of email to \"receiver@gmail.com\"",
		"\tset object of email to \"Welcome\"",
		"\tset body of email to \"Welcome on my new server!\"",
		"\tset attach file of email to \"plugin/Test/myfile.txt\""
})
@Since("1.0")

public class ScopeEmail extends EffectSection {

	public static EmailCreator lastEmailCreator;

	static {
		Skript.registerCondition(ScopeEmail.class,
				"(make|do|create) [new] [e]mail");
	}

	@Override
	public void execute(Event e) {
		lastEmailCreator = new EmailCreator();
		runSection(e);
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "make new email " + lastEmailCreator.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		if (checkIfCondition())
			return false;
		if (!hasSection()) {
			Skript.error("An email creation scope is useless without any content!");
			return false;
		}
		loadSection(true);
		return true;
	}
}