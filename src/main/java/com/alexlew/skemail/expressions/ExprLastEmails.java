package com.alexlew.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.effects.EffConnection;
import com.alexlew.skemail.events.javaxmail.MailFolder;
import org.bukkit.event.Event;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;

public class ExprLastEmails extends SimpleExpression<Message> {
	
	static {
		Skript.registerExpression(ExprLastEmails.class, Message.class, ExpressionType.SIMPLE,
				"last[ly] %integer% [e]mails [in ((folder|dir) %-string%|%-folder%)] [(using|with) (%-session%|%-string%)]",
				"[e]mails from %integer% to %integer% [in ((folder|dir) %-string%|%-folder%)] [(using|with) (%-session%|%-string%)]"
		);
	}
	
	private Expression<Integer> start;
	private Expression<Integer> end;
	private Expression<Object> dir;
	private Expression<Object> user;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		if (matchedPattern == 0) {
			start = (Expression<Integer>) expr[0];
			dir = (Expression<Object>) expr[1];
			user = (Expression<Object>) expr[2];
		} else {
			start = (Expression<Integer>) expr[0];
			end = (Expression<Integer>) expr[1];
			dir = (Expression<Object>) expr[2];
			user = (Expression<Object>) expr[3];
		}
		
		return true;
	}
	
	@Override
	protected Message[] get( Event e ) {
		Integer Start = start.getSingle(e);
		Integer End = end != null ? end.getSingle(e) : null;
		Object directory = dir != null ? dir.getSingle(e) : null;
		Object username = user != null ? user.getSingle(e) : null;
		Session session = null;
		Folder folder = null;
		Store store = null;
		List<Message> messages = new ArrayList<>();
		
		if (username != null) {
			if (username instanceof String) {
				session = EffConnection.accounts.get(username);
			} else {
				session = (Session) username;
			}
		} else {
			session = EffConnection.lastSession;
		}
		
		try {
			
			System.out.println(session.getTransport("imap").getURLName());
			System.out.println(session.getTransport("imaps").getURLName());
			store = session.getStore("imaps");
			store.connect(
					session.getTransport("imap").getURLName().getUsername(),
					session.getTransport("imap").getURLName().getPassword()
			);
			if (directory != null) {
				if (directory instanceof String) {
					folder = store.getFolder((String) directory);
				} else if (directory instanceof Folder) {
					folder = (Folder) directory;
				} else {
					folder = store.getDefaultFolder();
				}
			} else {
				folder = store.getDefaultFolder();
			}
			
			folder.addFolderListener(new MailFolder());
			
			if (End != null) {
				return folder.getMessages(Start, End);
			} else {
				for (int i = 0; i < Start; i++) {
					messages.add(folder.getMessage(i));
				}
				Message[] m = new Message[messages.size()];
				return messages.toArray(m);
			}
			
		} catch (MessagingException e1) {
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
		return null;
	}
}
