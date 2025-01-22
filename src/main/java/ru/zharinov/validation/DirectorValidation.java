package ru.zharinov.validation;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.CreateDirectorDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorValidation implements Validator<CreateDirectorDto> {
    private static final DirectorValidation INSTANCE = new DirectorValidation();


    @Override
    public ValidatorResult isValid(CreateDirectorDto object) {
        ValidatorResult validatorResult = new ValidatorResult();
        validationDate(object, validatorResult);
        validationName(object, validatorResult);
        return validatorResult;
    }

    private static void validationDate(CreateDirectorDto object, ValidatorResult validatorResult) {
        if (object.getDateOfBirthday() == null || object.getDateOfBirthday().isEmpty()) {
            validatorResult.add(ErrorInfo.of("invalid dateOfBirthday", "The date not must to be empty"));
        }
    }

    private static void validationName(CreateDirectorDto object, ValidatorResult validatorResult) {
        if (object.getName() == null || object.getName().trim().isEmpty()) {
            validatorResult.add(ErrorInfo.of("invalid.name", "The name not to be empty"));
        } else if (object.getName().length() < 4 || object.getName().length() > 50) {
            validatorResult.add(ErrorInfo.of("invalid.name", "The name must be between 4 and 50 characters"));
        }
    }

    public static DirectorValidation getInstance() {
        return INSTANCE;
    }
}
