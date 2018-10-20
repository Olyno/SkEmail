package com.alexlew.skemail.expressions.EmailInfos;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.skemail.types.EmailReader;

@Name("Author of Email")
@Description("Returns the author of an email.")
@Examples({
        "set {_author} to author of last email read"
})
@Since("1.?")

public class ExprAuthorOfEmail extends SimplePropertyExpression<EmailReader, String> {

    @Override
    public String convert( EmailReader email ) {
        if (email.getAuthor() != null) {
            return email.getAuthor();
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return "author";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}