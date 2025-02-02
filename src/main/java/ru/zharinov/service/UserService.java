package ru.zharinov.service;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zharinov.dao.UserDao;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.dto.user.UserDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.user.CreateUserMapper;
import ru.zharinov.mapper.user.UserMapper;
import ru.zharinov.validation.UserValidation;

import java.util.Optional;

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

    public static UserService getInstance() {
        return INSTANCE;
    }
}
