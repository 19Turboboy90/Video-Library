package ru.zharinov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.zharinov.validation.ErrorInfo;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateNotFoundException extends RuntimeException {
    private final List<ErrorInfo> errors;
}