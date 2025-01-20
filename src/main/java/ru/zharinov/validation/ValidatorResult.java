package ru.zharinov.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidatorResult {
    private final List<ErrorInfo> errors = new ArrayList<>();

    public void add(ErrorInfo error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
