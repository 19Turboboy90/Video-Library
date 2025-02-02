package ru.zharinov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.zharinov.validation.ErrorInfo;

import java.util.List;

@AllArgsConstructor
@Getter
public class NotFoundException extends RuntimeException {
    private final List<ErrorInfo> errors;
}