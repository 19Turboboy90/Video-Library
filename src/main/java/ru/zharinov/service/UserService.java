package ru.zharinov.service;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.dao.UserDao;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.dto.user.UserDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.user.CreateUserMapper;
import ru.zharinov.mapper.user.UserMapper;
import ru.zharinov.validation.EntityValidator;
import ru.zharinov.validation.UserValidation;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private static final CreateUserMapper creatUserMapper = CreateUserMapper.getInstance();
    private static final UserValidation userValidation = UserValidation.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findUserByEmailAndPassword(email, password).map(userMapper::mapper);
    }

    @SneakyThrows
    public Integer save(CreateUserDto userDto) {
        var valid = userValidation.isValid(userDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        var saveUser = creatUserMapper.mapper(userDto);
        var user = userDao.save(saveUser);
        return user.getId();
    }

    public List<UserDto> findAllUsers() {
        return Optional.of(userDao.findAll().stream().map(userMapper::mapper).toList()).orElse(emptyList());
    }

    public Optional<UserDto> findUserById(Integer userId) {
        EntityValidator.validateId(userId, "user");
        var user = userDao.findById(userId);
        EntityValidator.validateEntityExists(user, userId, "user");
        return user.map(userMapper::mapper);
    }

    public List<UserDto> findAllUsersByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return Optional.of(userDao.findAllUsersByPrefix(param).stream().map(userMapper::mapper).toList())
                .orElse(emptyList());
    }

    public void deleteUser(Integer userId) {
        userDao.delete(userId);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
