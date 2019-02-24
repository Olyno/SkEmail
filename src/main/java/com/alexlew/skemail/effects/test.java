package com.alexlew.skemail.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.skemail.SkEmail;
import org.bukkit.event.Event;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class test extends Effect {
	
	static {
		Skript.registerEffect(test.class, "just a test for %string%");
	}
	
	private Expression<String> mail;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
		mail = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	protected void execute( Event e ) {
		try {
			Session session = EffConnection.accounts.get(mail.getSingle(e));
			Transport transport = null;
			String[] attach_files = new String[0];
			transport = EffConnection.accounts.get(mail.getSingle(e)).getTransport("smtp");
			
			// Nouveau MimeMessage
			Message message = new MimeMessage(session);
			
			String author = "dsdsd";
			// On set la "from part"
			if (author != null) {
				message.setFrom(new InternetAddress(transport.getURLName().getUsername(), author));
			} else {
				message.setFrom(new InternetAddress(transport.getURLName().getUsername()));
			}
			
			// On set l'objet
			message.setSubject("aaaa");
			
			// On créer un body à ce Mime
			BodyPart messageBodyPart = new MimeBodyPart();
			
			// On set le content du body
			messageBodyPart.setContent("qqqqqq", "text/html; charset=UTF-8");
			
			// On créer plusieurs parties de ce Mime
			Multipart multipart = new MimeMultipart();
			
			// On ajoute la partie "body"
			multipart.addBodyPart(messageBodyPart);
			
			if (attach_files != null && attach_files.length > 0) {
				for (String filePath : attach_files) {
					MimeBodyPart attachPart = new MimeBodyPart();
					
					try {
						attachPart.attachFile(filePath);
					} catch (IOException e1) {
						SkEmail.error("The file path of file (" + filePath + ") doesn't exist or is not correct.");
						//e1.printStackTrace();
					}
					
					multipart.addBodyPart(attachPart);
				}
			}
			
			message.addRecipients(Message.RecipientType.TO, new Address[] {new InternetAddress("meiji.sakurai@gmail.com")});
			message.setContent(multipart);
			
			System.out.println(multipart.getBodyPart(0).getContentType());
			System.out.println(multipart.removeBodyPart(multipart.getBodyPart(0)));
			System.out.println(multipart.getCount());
			//Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
			//transport.sendMessage(message, message.getAllRecipients());
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (AddressException e1) {
			e1.printStackTrace();
		} catch (MessagingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public String toString( Event e, boolean debug ) {
		return null;
	}
	
}
