package ru.zharinov.service;

import lombok.SneakyThrows;
import ru.zharinov.dao.UserDao;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.dto.user.UserDto;
import ru.zharinov.entity.User;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.user.CreateUserMapper;
import ru.zharinov.mapper.user.UserMapper;
import ru.zharinov.mapper.user.UserWithFidbacksMapper;
import ru.zharinov.validation.EntityValidator;
import ru.zharinov.validation.UserValidation;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class UserService {
    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private static final CreateUserMapper creatUserMapper = CreateUserMapper.getInstance();
    private static final UserWithFidbacksMapper userWithFidbacksMapper = UserWithFidbacksMapper.getInstance();
    private static final UserValidation userValidation = UserValidation.getInstance();
    private final FeedbackService feedbackService = FeedbackService.getInstance();


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

    public void updateUser(CreateUserDto createUserDto) {
        var valid = userValidation.isValid(createUserDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        userDao.update(creatUserMapper.mapper(createUserDto));
    }

    public List<UserDto> findAllUsers() {
        return Optional.of(userDao.findAll().stream().map(userMapper::mapper).toList()).orElse(emptyList());
    }

    public Optional<UserDto> findUserById(Integer userId) {
        EntityValidator.validateId(userId, "user");
        var user = userDao.findById(userId);
        EntityValidator.validateEntityExists(user, userId, "user");
        var allFeedbackByUserId = feedbackService.findAllFeedbackByUserId(userId);
        user.ifPresent(u -> u.setFeedbacks(allFeedbackByUserId));
        return user.map(userWithFidbacksMapper::mapper);
    }

    public Optional<User> findById(Integer userId) {
        EntityValidator.validateId(userId, "userId");
        return userDao.findById(userId);
    }

    public List<UserDto> findAllUsersByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return Optional.of(userDao.findAllUsersByPrefix(param).stream().map(userMapper::mapper).toList())
                .orElse(emptyList());
    }

    public void deleteUser(Integer userId) {
        userDao.delete(userId);
    }
}
