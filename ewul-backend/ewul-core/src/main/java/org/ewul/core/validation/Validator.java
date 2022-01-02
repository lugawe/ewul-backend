package org.ewul.core.validation;

public interface Validator<T> {

    T get() throws ValidationException;

}
