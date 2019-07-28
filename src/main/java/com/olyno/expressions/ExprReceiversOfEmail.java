package com.olyno.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.olyno.SkEmail;
import com.olyno.types.IAddress;
import org.bukkit.event.Event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Name("Receiver/Recipients of Email")
@Description("Returns the receivers/recipients of an email. Can be set in a email scope")
@Examples({
        "make new email:",
        "\tset receiver of email to \"myfriend@gmail.com\""
})
@Since("1.0")

public class ExprReceiversOfEmail extends SimpleExpression<Address> {

    static {
        Skript.registerExpression(ExprReceiversOfEmail.class, Address.class, ExpressionType.SIMPLE,
                "%email%'s (receiver|recipient)[s]",
                "(receiver|recipient)[s] of %email%");
    }

    private Expression<Message> message;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) expr[0];
        return true;
    }

    @Override
    protected Address[] get(Event e) {
        try {
            Message email = message.getSingle(e);
            return email.getAllRecipients();
        } catch (MessagingException e1) {
            return null;
        }
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Object.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Message email = message.getSingle(e);
        try {
            List<Address> addressesTo = new LinkedList<>();
            List<Address> addressesCc = new LinkedList<>();
            List<Address> addressesBcc = new LinkedList<>();
            if (email.getRecipients(Message.RecipientType.TO) != null) {
                addressesTo.addAll(Arrays.asList(email.getRecipients(Message.RecipientType.TO)));
            }
            if (email.getRecipients(Message.RecipientType.CC) != null) {
                addressesCc.addAll(Arrays.asList(email.getRecipients(Message.RecipientType.CC)));
            }
            if (email.getRecipients(Message.RecipientType.BCC) != null) {
                addressesBcc.addAll(Arrays.asList(email.getRecipients(Message.RecipientType.BCC)));
            }

            switch (mode) {
                case SET:
                    addressesTo = new LinkedList<>();
                    addressesCc = new LinkedList<>();
                    addressesBcc = new LinkedList<>();
                    if (delta.length > 1) {
                        for (Object o : delta) {
                            if (o instanceof String) {
                                addressesTo.add(new InternetAddress((String) o));
                            } else if (o instanceof IAddress) {
                                IAddress a = (IAddress) o;
                                if (a.getType() == Message.RecipientType.TO) {
                                    addressesTo.add(a.getAddress());
                                } else if (a.getType() == Message.RecipientType.CC) {
                                    addressesCc.add(a.getAddress());
                                } else if (a.getType() == Message.RecipientType.BCC) {
                                    addressesBcc.add(a.getAddress());
                                }
                            }
                        }
                        email.setRecipients(Message.RecipientType.TO,
                                addressesTo.toArray(new InternetAddress[addressesTo.size()]));
                        email.setRecipients(Message.RecipientType.CC,
                                addressesCc.toArray(new InternetAddress[addressesCc.size()]));
                        email.setRecipients(Message.RecipientType.BCC,
                                addressesBcc.toArray(new InternetAddress[addressesBcc.size()]));
                    } else {
                        if (delta[0] instanceof String) {
                            email.setRecipient(Message.RecipientType.TO, new InternetAddress((String) delta[0]));
                        } else if (delta[0] instanceof IAddress) {
                            IAddress a = (IAddress) delta[0];
                            email.setRecipient(a.getType(), a.getAddress());
                        } else {
                            SkEmail.error("The type of this receiver is not a IAddress or a string, but a " + delta[0].getClass().getName());
                        }
                    }
                    break;
                case ADD:
                    for (Object o : delta) {
                        if (o instanceof String) {
                            InternetAddress address = new InternetAddress((String) o);
                            address.validate();
                            addressesTo.add(address);
                        } else if (o instanceof IAddress) {
                            IAddress a = (IAddress) o;
                            if (a.getType() == Message.RecipientType.TO) {
                                addressesTo.add(a.getAddress());
                            } else if (a.getType() == Message.RecipientType.CC) {
                                addressesCc.add(a.getAddress());
                            } else if (a.getType() == Message.RecipientType.BCC) {
                                addressesBcc.add(a.getAddress());
                            }
                        } else {
                            SkEmail.error("You can't add a receiver (recipient) of type " + o.getClass().getName() + " because it's not a type String or IAddress.");
                        }
                    }
                    email.setRecipients(Message.RecipientType.TO,
                            addressesTo.toArray(new InternetAddress[addressesTo.size()]));
                    email.setRecipients(Message.RecipientType.CC,
                            addressesCc.toArray(new InternetAddress[addressesCc.size()]));
                    email.setRecipients(Message.RecipientType.BCC,
                            addressesBcc.toArray(new InternetAddress[addressesBcc.size()]));

                    break;
                case REMOVE:
                    for (Object o : delta) {
                        if (o instanceof String) {
                            InternetAddress address = new InternetAddress((String) o);
                            address.validate();
                            addressesTo.remove(address);
                        } else if (o instanceof IAddress) {
                            IAddress a = (IAddress) o;
                            if (a.getType() == Message.RecipientType.TO) {
                                addressesTo.remove(a.getAddress());
                            } else if (a.getType() == Message.RecipientType.CC) {
                                addressesCc.remove(a.getAddress());
                            } else if (a.getType() == Message.RecipientType.BCC) {
                                addressesBcc.remove(a.getAddress());
                            }
                        } else {
                            SkEmail.error("You can't add a receiver (recipient) of type " + o.getClass().getName() + " because it's not a type String or IAddress.");
                        }
                    }
                    email.setRecipients(Message.RecipientType.TO,
                            addressesTo.toArray(new InternetAddress[addressesTo.size()]));
                    email.setRecipients(Message.RecipientType.CC,
                            addressesCc.toArray(new InternetAddress[addressesCc.size()]));
                    email.setRecipients(Message.RecipientType.BCC,
                            addressesBcc.toArray(new InternetAddress[addressesBcc.size()]));
                    break;
                case DELETE:
                    email.setRecipient(Message.RecipientType.TO, null);
                    break;
                default:
                    break;
            }
        } catch (AddressException e1) {
            SkEmail.error("This email address is not valid: " + delta[0]);
        } catch (MessagingException ignored) {
        }
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends InternetAddress> getReturnType() {
        return InternetAddress.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "receivers of email";
    }

}



