package com.alexlew.skemail.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.mail.*;
import java.util.Properties;

public class test extends Effect {
	
	static {
		Skript.registerEffect(test.class, "user[name] %string% password %string% [e]mail %email%");
	}
	
	private Expression<String> user;
	private Expression<String> pass;
	private Expression<Message> message;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		user = (Expression<String>) expr[0];
		pass = (Expression<String>) expr[1];
		message = (Expression<Message>) expr[2];
		return true;
	}
	
	@Override
	protected void execute( Event e ) {
		String username = user.getSingle(e);
		String password = pass.getSingle(e);
		Message email = message.getSingle(e);
		
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							PasswordAuthentication pwd = new PasswordAuthentication(username, password);
							System.out.println("PWD: ");
							System.out.println(pwd.getUserName());
							System.out.println(pwd.getPassword());
							return pwd;
						}
					});
			
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
			Transport.send(email);
			
		} catch (MessagingException e1) {
			e1.printStackTrace();
		} finally {
		}
	}
	
	@Override
	public String toString( Event e, boolean debug ) {
		return null;
	}
	
}
