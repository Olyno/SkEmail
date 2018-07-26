package com.alexlew.skemail.types;

import java.io.File;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.ParseContext;

public class EmailBuilder {
	
	static {
		new SimpleType<EmailBuilder>(EmailBuilder.class, "emailbuilder", "emailbuilders?") {
			public EmailBuilder parse(String s, ParseContext pc) {
				return null;
			}

			@Override
			public boolean canParse(ParseContext pc) {
				return false;
			}

			@Override
			public String toString(EmailBuilder builder, int arg1) {
				return "email";
			}

		}.changer(new Changer<EmailBuilder>() {
			@Override
			public Class<?>[] acceptChange(ChangeMode mode) {
				return mode != ChangeMode.ADD ? null : new Class[]{EmailBuilder.class};
			}

			@Override
			public void change(EmailBuilder[] what, Object[] delta, ChangeMode mode) {
				
			}
		});
		/*Classes.registerClass(new ClassInfo<>(EmailBuilder.class, "emailbuilder")
				.user("emailbuilder[s]")
				.name("EmailBuilder")
				.description("That's the email's builder")
				.usage("Return the builder of an email")
				.examples("send last email")
				.since("1.0")
				.defaultExpression(new EventValueExpression<>(EmailBuilder.class))
				.parser(new Parser<EmailBuilder>() {
					@Override
					public EmailBuilder parse(final String s, final ParseContext context) {
						return null;
					}
					
					@Override
					public boolean canParse(final ParseContext context) {
						return false;
					}
					
					@Override
					public String toVariableNameString(final EmailBuilder e) {
						return null;
					}
					
					@Override
					public String getVariableNamePattern() {
						return null;
					}
					
					@Override
					public String toString(final EmailBuilder e, final int flags) {
						return "builder: " + e;
					}
					
					
				}));*/
	}
	
	//private static EmailBuilder builder;
	
	private String author;
	private String receiver;
	private String object;
	private String body;
	private File attach_file;
	
	public EmailBuilder() {}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String title) {
		this.object = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public File getAttach_file() {
		return attach_file;
	}
	public void setAttach_file(File attach_file) {
		this.attach_file = attach_file;
	}
	public boolean isEmpty() {
		return author == null 
				&& receiver == null 
				&& object == null
				&& body == null
				&& attach_file == null;
	}
	
	public void setEmpty(EmailBuilder builder) {
		builder = null;
	}
	
	public void setEmpty(String param, EmailBuilder builder) {
		if(param == "author") {
			author = builder.getAuthor();
			author = null;
		} else if (param == "body") {
			body = builder.getBody();
			body = null;
		} else if (param == "receiver") {
			receiver = builder.getReceiver();
			receiver = null;
		} else if (param == "object") {
			object = builder.getObject();
			object = null;
		} else if (param == "attach_file") {
			attach_file = builder.getAttach_file();
			attach_file = null;
		}
	}
	/*public EmailBuilder getBuilder() {
	//	return builder;
	}*/
	
}
