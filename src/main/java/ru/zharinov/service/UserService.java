package ru.zharinov.service;

import lombok.SneakyThrows;
import ru.zharinov.dao.UserDao;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.dto.user.UserDto;
import ru.zharinov.entity.User;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.UserMapper;
import ru.zharinov.validation.EntityValidator;
import ru.zharinov.validation.UserValidation;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class UserService {
    private final UserDao userDao;
    private static final UserValidation userValidation = UserValidation.getInstance();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.findUserByEmailAndPassword(email, password).map(UserMapper::toUserDto);
    }

    @SneakyThrows
    public Integer save(CreateUserDto userDto) {
        var valid = userValidation.isValid(userDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        var saveUser = UserMapper.toUser(userDto);
        var user = userDao.save(saveUser);
        return user.getId();
    }

    public void updateUser(CreateUserDto createUserDto) {
        var valid = userValidation.isValid(createUserDto);
        if (!valid.isValid()) {
            throw new NotFoundException(valid.getErrors());
        }
        userDao.update(UserMapper.toUser(createUserDto));
    }

    public List<UserDto> findAllUsers() {
        return Optional.of(userDao.findAll().stream().map(UserMapper::toUserDto).toList()).orElse(emptyList());
    }

    public Optional<UserDto> findUserById(Integer userId) {
        EntityValidator.validateId(userId, "user");
        var user = userDao.findById(userId);
        EntityValidator.validateEntityExists(user, userId, "user");
        var allFeedbackByUserId =
                FactoryService.getInstance().getFeedbackService().findAllFeedbackByUserId(userId);
        user.ifPresent(u -> u.setFeedbacks(allFeedbackByUserId));
        return user.map(UserMapper::toUserDtoWithFeedbacks);
    }

    public Optional<User> findById(Integer userId) {
        EntityValidator.validateId(userId, "userId");
        return userDao.findById(userId);
    }

    public List<UserDto> findAllUsersByPrefix(String prefix) {
        var param = EntityValidator.validatorPrefix(prefix);
        return Optional.of(userDao.findAllUsersByPrefix(param).stream().map(UserMapper::toUserDto).toList())
                .orElse(emptyList());
    }

    public void deleteUser(Integer userId) {
        userDao.delete(userId);
    }
}
