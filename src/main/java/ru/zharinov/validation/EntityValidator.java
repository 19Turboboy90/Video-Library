package ru.zharinov.validation;

import lombok.experimental.UtilityClass;
import ru.zharinov.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class EntityValidator {
    public static <T> void validateId(T id, String entityName) {
        if (id == null) {
            throw new NotFoundException(
                    List.of(ErrorInfo.of("Error in the ID", entityName + " ID cannot be null"))
            );
        }
    }

    public static <T> void validateEntityExists(Optional<?> entity, T id, String entityName) {
        if (entity.isEmpty()) {
            throw new NotFoundException(
                    List.of(ErrorInfo.of("Error in the ID", "Not found " + entityName + " with id = " + id))
            );
        }
    }

    public static String validatorPrefix(String param) {
        return param == null || param.isEmpty() ? "" : param;
    }
}
