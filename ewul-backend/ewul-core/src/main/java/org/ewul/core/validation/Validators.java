package org.ewul.core.validation;

import java.util.regex.Pattern;

public final class Validators {

    private Validators() {
    }

    public static Validator<String> regexValidator(String value, Pattern regexPattern) {
        return new RegexValidator(value, regexPattern);
    }

    public static Validator<String> regexValidator(String value, String regex) {
        return new RegexValidator(value, Pattern.compile(regex));
    }

}
