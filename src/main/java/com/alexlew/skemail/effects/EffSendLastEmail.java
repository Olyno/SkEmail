package com.alexlew.skemail.effects;

import java.util.Properties;

import org.bukkit.event.Event;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import org.simplejavamail.util.ConfigLoader;
import javax.mail.internet.MimeMessage.RecipientType;

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
		String receiver = email.getSingle(e).getReceiver();
		String object = email.getSingle(e).getObject();
		String body = email.getSingle(e).getBody();
		//String html_content = email.getSingle(e).getHtml_content();
		
		if (receiver != null) {
			if(object!=null) {
				if(body==null) {
					Skript.warning("It is recommended to put a content to your email");
				}
					
				Properties props = new Properties();
			   props.put("mail.smtp.host", "smtp.gmail.com");
			   props.put("mail.smtp.starttls.enable", "true");
			   props.put("mail.smtp.user", EffConnexion.username);
			   props.put("mail.smtp.password", EffConnexion.password);
			   props.put("mail.smtp.port", "587");
			   props.put("mail.smtp.auth", "true");
				   
			   Session session = Session.getDefaultInstance(props);
			   MimeMessage msg = new MimeMessage(session);
			   try {
					msg.setFrom(new InternetAddress((EffConnexion.username + "@gmail.com")));
					//TODO Loop all adress
					msg.addRecipient(RecipientType.TO, new InternetAddress(receiver));
					msg.setSubject(object);
					msg.setText(body);
					Transport t = session.getTransport("smtp");
					t.connect("smtp.gmail.com", EffConnexion.username, EffConnexion.password);
					t.sendMessage(msg, msg.getAllRecipients());
					t.close();
					
				   } catch (AddressException e1) {
							e1.printStackTrace();
						} catch (MessagingException e1) {
							e1.printStackTrace();
						}
						
			   
			  } else {
				Skript.error("You must precise the object/subject of your email!");
			}
			
		} else {
			Skript.error("You must precise the mail which will receive your email!");
		
		}
}

}
