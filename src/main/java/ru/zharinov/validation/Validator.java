package ru.zharinov.validation;

public interface Validator<T> {
    ValidatorResult isValid(T object);
}
