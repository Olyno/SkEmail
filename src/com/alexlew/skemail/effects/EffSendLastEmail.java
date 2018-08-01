package com.alexlew.skemail.effects;

import org.bukkit.event.Event;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
//import org.simplejavamail.util.ConfigLoader;

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
		String html_content = email.getSingle(e).getHtml_content();
		//File attach_file = email.getSingle(e).getAttach_file();
		
		if(author!=null) {
			if (receiver != null) {
				if(object!=null) {
					if(body==null) {
						Skript.warning("It is recommended to put a content to your email");
					}
					
					//CODE
					//ConfigLoader.loadProperties("simplejavamail.properties", true); // optional default
					//ConfigLoader.loadProperties("overrides.properties", true); // optional extra

					String[] receiver_name1 = receiver.split("@");
					String receiver_name = receiver_name1[0];
					
					String[] host1 = author.split("@");
					String host = "smtp." + host1[1];
					
					Email mail = EmailBuilder.startingBlank()
					          .to(receiver_name, receiver)
					          .withSubject(object)
					          .withHTMLText(html_content)
					          .withPlainText(body)
					          //.withEmbeddedImage("wink1", imageByteArray, "image/png")
					          //.withEmbeddedImage("wink2", imageDatesource)
					          //.withAttachment("invitation", pdfByteArray, "application/pdf")
					          //.withAttachment("dresscode", odfDatasource)
					          .withHeader("X-Priority", 5)
					          .withReturnReceiptTo()
					          .buildEmail();

					Mailer mailer = MailerBuilder
					          .withSMTPServer(host, 587, EffConnexion.username, EffConnexion.password)
					          .withTransportStrategy(TransportStrategy.SMTP_TLS)
					          .withSessionTimeout(10 * 1000)
					          .clearEmailAddressCriteria() // turns off email validation
					          .withDebugLogging(true)
					          .buildMailer();

					mailer.sendMail(mail);
				   
					
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
