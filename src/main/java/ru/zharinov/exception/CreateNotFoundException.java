package ru.zharinov.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class CreateNotFoundException {
    private final List<ErrorInfo> errors = new ArrayList<>();

    public void add(ErrorInfo errorInfo) {
        this.errors.add(errorInfo);
    }
}