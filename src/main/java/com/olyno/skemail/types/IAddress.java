package com.olyno.skemail.types;

import com.olyno.skemail.SkEmail;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import jakarta.mail.Message;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public class IAddress {

    static {
        Classes.registerClass(new ClassInfo<>(IAddress.class, "iaddress")
                .defaultExpression(new EventValueExpression<>(IAddress.class))
                .user("i\\-?address")
                .name("IAddress")
                .parser(new Parser<IAddress>() {

                    @Override
                    public IAddress parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(IAddress address, int arg1) {
                        return address.getAddress().getAddress();
                    }

                    @Override
                    public String toVariableNameString(IAddress address) {
                        return address.getAddress().getAddress();
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
