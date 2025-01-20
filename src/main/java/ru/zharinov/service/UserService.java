package ru.zharinov.service;

import lombok.NoArgsConstructor;
import ru.zharinov.dao.UserDao;
import ru.zharinov.dto.UserDto;
import ru.zharinov.mapper.UserMapper;
import ru.zharinov.validation.UserValidation;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findUserByEmailAndPassword(email, password).map(userMapper::mapper);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
