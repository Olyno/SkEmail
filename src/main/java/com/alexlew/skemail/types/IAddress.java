package com.alexlew.skemail.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.alexlew.skemail.SkEmail;

import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class IAddress {

	static {
		Classes.registerClass(new ClassInfo<>(IAddress.class, "iaddress")
				.defaultExpression(new EventValueExpression<>(IAddress.class))
				.user("i\\-?address")
				.name("IAddress")
				.parser(new Parser<IAddress>() {

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public IAddress parse(String arg0, ParseContext arg1) {
						return null;
					}

					@Override
					public String toString(IAddress arg0, int arg1) {
						return null;
					}

					@Override
					public String toVariableNameString(IAddress arg0) {
						return null;
					}

				}));
	}

	private Message.RecipientType type;
	private InternetAddress address;

	public IAddress(InternetAddress address, Message.RecipientType type) {
		try {
			address.validate();
			this.type = type;
			this.address = address;

		} catch (AddressException e) {
			SkEmail.error("This email address is not valid: " + address.getAddress());
		}
	}

	public Message.RecipientType getType() {
		return type;
	}

	public InternetAddress getAddress() {
		return address;
	}
}
