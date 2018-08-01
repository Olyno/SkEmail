package com.alexlew.skemail.types;

import java.io.File;

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
				return ScopeEmail.lastEmail.getAuthor();
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
            ScopeEmail.lastEmail.author = builder.author;
            ScopeEmail.lastEmail.body = builder.body;
            ScopeEmail.lastEmail.object = builder.object;
            ScopeEmail.lastEmail.receiver = builder.receiver;
            ScopeEmail.lastEmail.attach_file = builder.attach_file;
        }
    }
	
	private String author, body, object, receiver, infos;
	private File attach_file;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		ScopeEmail.lastEmail.author = author;
	}
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
	public File getAttach_file() {
		return attach_file;
	}
	public void setAttach_file(File attach_file) {
		ScopeEmail.lastEmail.attach_file = attach_file;
	}

	public String getInfos() {
		infos = "Author:" + ScopeEmail.lastEmail.getAuthor() + 
				";Body:" + ScopeEmail.lastEmail.getBody() + 
				";Object:" + ScopeEmail.lastEmail.getObject() + 
				";Attach_file:" + ScopeEmail.lastEmail.attach();
		return infos;
	}

	private Boolean attach() {
		File attach = ScopeEmail.lastEmail.getAttach_file();
		if(attach == null) {
			return false;
		} else {
			return true;
		}
	}

}
