package com.alexlew.skemail.types;

import com.alexlew.skemail.effects.EffConnexion;
import com.alexlew.skemail.scopes.ScopeEmail;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class EmailBuilderbase {
	
	static {
		Classes.registerClass(new ClassInfo<EmailBuilderbase>(EmailBuilderbase.class, "emailbuilderbase")
				.user("emailbuilderbase")
				.name("emailbuilderbase")
				.parser(new Parser<EmailBuilderbase>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			public EmailBuilderbase parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(EmailBuilderbase arg0, int arg1) {
				return EffConnexion.username;
			}

			@Override
			public String toVariableNameString(EmailBuilderbase arg0) {
				return null;
			}
		   
		}));
	}
	
	public EmailBuilderbase() { }

    public EmailBuilderbase(EmailBuilderbase builder)
    {
        if (builder != null)
        {
            ScopeEmail.lastEmail.body = builder.body;
            ScopeEmail.lastEmail.object = builder.object;
            ScopeEmail.lastEmail.receiver = builder.receiver;
            ScopeEmail.lastEmail.html_content = builder.html_content;
        }
    }
	
	private String body, object, receiver, infos, html_content;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		ScopeEmail.lastEmail.body = body;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		ScopeEmail.lastEmail.object = object;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		ScopeEmail.lastEmail.receiver = receiver;
	}
	public String getHtml_content() {
		return html_content;
	}
	public void setHtml_content(String receiver) {
		ScopeEmail.lastEmail.html_content = html_content;
	}

	public String getInfos() {
		infos = "Receiver:" + ScopeEmail.lastEmail.getReceiver() + 
				";Body:" + ScopeEmail.lastEmail.getBody() + 
				";Object:" + ScopeEmail.lastEmail.getObject() + 
				";Html_content:" + ScopeEmail.lastEmail.getHtml_content();
		return infos;
	}

}
