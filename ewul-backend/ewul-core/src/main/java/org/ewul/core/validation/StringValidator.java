package org.ewul.core.validation;

public abstract class StringValidator implements Validator<String> {

    protected final String value;

    public StringValidator(String value) {
        this.value = value;
    }

}
