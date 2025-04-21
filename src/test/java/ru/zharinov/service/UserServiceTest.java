package ru.zharinov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zharinov.dao.UserDao;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.entity.Feedback;
import ru.zharinov.entity.Role;
import ru.zharinov.entity.User;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final Feedback FEEDBACK_1 =
            new Feedback(2, "testFeedback2", 10, null, new User());
    private static final User USER =
            new User(1, "testUser", "testEmail@mail.ru", "111111", Role.USER, List.of(FEEDBACK_1));

    @Mock
    private UserDao userDao;
    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService.setFeedbackService(feedbackService);
    }

    @Test
    void testServiceInjection() {
        assertThat(feedbackService).isNotNull();
    }

    @Test
    void login() {
        when(userDao.findUserByEmailAndPassword(USER.getEmail(), USER.getPassword())).thenReturn(Optional.of(USER));

        var login = userService.login("testEmail@mail.ru", "111111");

        assertThat(login).isEqualTo(Optional.of(UserMapper.toUserDto(USER)));
    }

    @Test
    void loginWhenEmailIsNotCorrect() {
        when(userDao.findUserByEmailAndPassword("testEmai@mail.ru", USER.getPassword())).thenReturn(Optional.empty());

        var login = userService.login("testEmai@mail.ru", "111111");

        assertThat(login).isEqualTo(Optional.empty());
    }

    @Test
    void loginWhenPasswordIsNotCorrect() {
        when(userDao.findUserByEmailAndPassword(USER.getEmail(), "11")).thenReturn(Optional.empty());

        var login = userService.login(USER.getEmail(), "11");

        assertThat(login).isEqualTo(Optional.empty());
    }

    @Test
    void save() {
        when(userDao.save(any(User.class))).thenReturn(USER);

        var saveUser = userService.save(getCreateUserDto());

        assertThat(saveUser).isEqualTo(USER.getId());
    }

    @Test
    void updateUser() {
        doNothing().when(userDao).update(any(User.class));

        userService.updateUser(getCreateUserDto());
        verify(userDao).update(any(User.class));
    }

    @Test
    void findAllUsers() {
        when(userDao.findAll()).thenReturn(List.of(USER));

        var allUsers = userService.findAllUsers();

        assertThat(allUsers).isEqualTo(List.of(UserMapper.toUserDto(USER)));
    }

    @Test
    void findUserById() {
        when(userDao.findById(USER.getId())).thenReturn(Optional.of(USER));
        when(feedbackService.findAllFeedbackByUserId(USER.getId())).thenReturn(List.of(FEEDBACK_1));

        var userById = userService.findUserById(USER.getId());
        var userDto = UserMapper.toUserDtoWithFeedbacks(USER);

        assertThat(userById).isEqualTo(Optional.of(userDto));
    }

    @Test
    void findById() {
        when(userDao.findById(USER.getId())).thenReturn(Optional.of(USER));


        var userById = userService.findById(USER.getId());

        assertThat(userById).isEqualTo(Optional.of(USER));
    }

    @Test
    void findUserWhenIdIsNotExist() {
        when(userDao.findById(-1)).thenReturn(Optional.empty());

        var userById = userService.findById(-1);

        assertThat(userById).isEqualTo(Optional.empty());
    }

    @Test
    void findAllUsersByPrefix() {
        when(userDao.findAllUsersByPrefix("test")).thenReturn((List.of(USER)));

        var listUsers = userService.findAllUsersByPrefix("test");
        var userDto = UserMapper.toUserDto(USER);

        assertThat(listUsers).isEqualTo(List.of(userDto));
    }

    @Test
    void findAllUsersByPrefixIfPrefixIsEmpty() {
        when(userDao.findAllUsersByPrefix("")).thenReturn((List.of()));

        var listUsers = userService.findAllUsersByPrefix("");

        assertThat(listUsers).isEqualTo(List.of());
    }

    @Test
    void deleteUser() {
        when(userDao.delete(USER.getId())).thenReturn(true);

        var deleteUser = userService.deleteUser(USER.getId());

        assertThat(deleteUser).isTrue();

        verify(userDao).delete(USER.getId());
    }

    @Test
    void deleteIsNotExistUser() {
        when(userDao.delete(-1)).thenReturn(false);

        var deleteUser = userService.deleteUser(-1);

        assertThat(deleteUser).isFalse();

        verify(userDao).delete(-1);
    }

    @Test
    void checkUserId() {
        assertThrows(NotFoundException.class, () -> userService.deleteUser(null));
    }

    private CreateUserDto getCreateUserDto() {
        return CreateUserDto.builder()
                .id(USER.getId().toString())
                .name(USER.getName())
                .email(USER.getEmail())
                .password(USER.getPassword())
                .role(USER.getRole().toString())
                .build();
    }
}