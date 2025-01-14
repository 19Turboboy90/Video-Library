package ru.zharinov.exception;

import lombok.Value;

@Value(staticConstructor = "of")
public class ErrorInfo {
    String code;
    String message;
}
