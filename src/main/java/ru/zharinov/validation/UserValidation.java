package ru.zharinov.validation;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.user.CreateUserDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserValidation implements Validator<CreateUserDto> {
    private static final UserValidation INSTANCE = new UserValidation();

    @Override
    public ValidatorResult isValid(CreateUserDto object) {
        ValidatorResult validatorResult = new ValidatorResult();
        validationName(object, validatorResult);
        validationEmail(object, validatorResult);
        validationPassword(object, validatorResult);
        return validatorResult;
    }

    private static void validationEmail(CreateUserDto object, ValidatorResult validatorResult) {
        if (object.getEmail() == null || !object.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            validatorResult.add(ErrorInfo.of("invalid.email", "The email not must to be empty"));
        }
    }

    private static void validationName(CreateUserDto object, ValidatorResult validatorResult) {
        if (object.getName() == null || object.getName().trim().isEmpty()) {
            validatorResult.add(ErrorInfo.of("invalid.name", "The name not to be empty"));
        } else if (object.getName().length() < 4 || object.getName().length() > 50) {
            validatorResult.add(ErrorInfo.of("invalid.name", "The name must be between 4 and 50 characters"));
        }
    }

    private static void validationPassword(CreateUserDto object, ValidatorResult validatorResult) {
        if (object.getPassword() == null || object.getPassword().trim().isEmpty()) {
            validatorResult.add(ErrorInfo.of("invalid.password", "The password not to be empty"));
        } else if (object.getPassword().length() < 6 || object.getPassword().length() > 50) {
            validatorResult.add(ErrorInfo.of("invalid.password", "The password must be between 6 and 50 characters"));
        }
    }

    public static UserValidation getInstance() {
        return INSTANCE;
    }
}
