package com.alexlew.skemail.effects;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.bukkit.event.Event;

import com.alexlew.skemail.types.EmailBuilderbase;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Send EmailBuilderbase")
@Description("Send the email")
@Examples({
			"send last email"
	})
@Since("1.0")

public class EffSendLastEmail extends Effect {

	static {
		Skript.registerEffect(EffSendLastEmail.class, 
				"send %emailbuilderbase%");
	}
	
	private Expression<EmailBuilderbase> email;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		email = (Expression<EmailBuilderbase>) expr[0];
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "send " + e.toString();
	}

	@Override
	protected void execute(Event e) {
		String author = email.getSingle(e).getAuthor();
		String receiver = email.getSingle(e).getReceiver();
		String object = email.getSingle(e).getObject();
		String body = email.getSingle(e).getBody();
		//File attach_file = email.getSingle(e).getAttach_file();
		
		if(author!=null) {
			if (receiver != null) {
				if(object!=null) {
					if(body==null) {
						Skript.warning("It is recommended to put a content to your email");
					}
					
					//CODE
					Properties props = new Properties();
				    props.put("mail.smtp.host", "localhost");
				    Session session = Session.getInstance(props, null);

				    try {
				        MimeMessage msg = new MimeMessage(session);
				        msg.setFrom(author);
				        msg.setRecipients(Message.RecipientType.TO,
				                          receiver);
				        msg.setSubject(object);
				        msg.setText(body);
				        Transport.send(msg, EffConnexion.username, EffConnexion.password);
				    } catch (MessagingException mex) {
				        System.out.println("send failed, exception: " + mex);
				    }
				   
					
				} else {
					Skript.error("You must precise the object/subject of your email!");
				}
				
			} else {
				Skript.error("You must precise the mail which will receive your email!");
			}
			
		} else {
			Skript.error("You must precise the author of your email!");
		}
			
		Skript.warning(email.getSingle(e).getInfos());
	}

}
