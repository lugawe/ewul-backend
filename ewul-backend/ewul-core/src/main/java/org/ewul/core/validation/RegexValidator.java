package org.ewul.core.validation;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegexValidator extends StringValidator {

    protected final Pattern regexPattern;

    public RegexValidator(String value, Pattern regexPattern) {
        super(value);
        this.regexPattern = Objects.requireNonNull(regexPattern);
    }

    @Override
    public String get() throws ValidationException {
        if (!regexPattern.matcher(value).matches()) {
            throw new ValidationException("supplied value does not match required regex pattern");
        }
        return value;
    }

}
