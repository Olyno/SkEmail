package com.alexlew.skemail.types;

import com.alexlew.skemail.effects.EffConnection;
import com.alexlew.skemail.scopes.ScopeEmail;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import org.apache.commons.lang.ArrayUtils;

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
				return EffConnection.username;
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
            ScopeEmail.lastEmail.receivers = builder.receivers;
			ScopeEmail.lastEmail.attach_files = builder.attach_files;
        }
    }
	
	private String body;
	private String object;
	private String[] receivers;
	private String[] attach_files;


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

	public String getInfos() {
		String infos = "Receivers:" + ScopeEmail.lastEmail.getReceivers().toString() +
				";Body:" + ScopeEmail.lastEmail.getBody() +
				";Object:" + ScopeEmail.lastEmail.getObject() +
				";Attachments:" + ScopeEmail.lastEmail.getAttachments().toString();
		return infos;
	}

	public void setAttachment(String attach_file) {
		ScopeEmail.lastEmail.attach_files = new String[]{attach_file};
	}

	public String[] getAttachments() {
		return attach_files;
	}
	public void addAttachment(String attach_file) {
		if (ScopeEmail.lastEmail.attach_files != null) {
			if (ScopeEmail.lastEmail.attach_files.length > 0) {
				for (int i = 0; i < ScopeEmail.lastEmail.attach_files.length; i ++) {
					if (ScopeEmail.lastEmail.attach_files[i] == attach_file) {
						ScopeEmail.lastEmail.attach_files =
								new String[]{ArrayUtils.remove(ScopeEmail.lastEmail.attach_files, i).toString()};
						break;
					}
				}
			}
		}

		ScopeEmail.lastEmail.attach_files =
				new String[]{ArrayUtils.add(ScopeEmail.lastEmail.attach_files, attach_file).toString()};


	}
	public void removeAttachment(String attach_file) {
		if (ScopeEmail.lastEmail.attach_files != null) {
			if (ScopeEmail.lastEmail.attach_files.length > 0) {
				for (int i = 0; i < ScopeEmail.lastEmail.attach_files.length; i ++) {
					if (ScopeEmail.lastEmail.attach_files[i] == attach_file) {
						ScopeEmail.lastEmail.attach_files =
								new String[]{ArrayUtils.remove(ScopeEmail.lastEmail.attach_files, i).toString()};
						break;
					}
				}
			}
		}


	}

	public void setReceiver(String receiver) {
		ScopeEmail.lastEmail.receivers = new String[]{receiver};
	}
	public String[] getReceivers() {
		return receivers;
	}
	public void addReceiver(String receiver) {
		if (ScopeEmail.lastEmail.receivers != null) {
			if (ScopeEmail.lastEmail.receivers.length > 0) {
				for (int i = 0; i < ScopeEmail.lastEmail.receivers.length; i ++) {
					if (ScopeEmail.lastEmail.receivers[i] == receiver) {
						ScopeEmail.lastEmail.receivers =
								new String[]{ArrayUtils.remove(ScopeEmail.lastEmail.receivers, i).toString()};
						break;
					}
				}
			}
		}

		ScopeEmail.lastEmail.receivers =
				new String[]{ArrayUtils.add(ScopeEmail.lastEmail.receivers, receiver).toString()};
	}

	public void removeReceiver(String receiver) {
		if (ScopeEmail.lastEmail.receivers != null) {
			if (ScopeEmail.lastEmail.receivers.length > 0) {
				for (int i = 0; i < ScopeEmail.lastEmail.receivers.length; i ++) {
					if (ScopeEmail.lastEmail.receivers[i] == receiver) {
						ScopeEmail.lastEmail.receivers =
								new String[]{ArrayUtils.remove(ScopeEmail.lastEmail.receivers, i).toString()};
						break;
					}
				}
			}
		}


	}

}
