package main.java.ru.video.library;

import main.java.ru.video.library.dao.UserDao;
import main.java.ru.video.library.entity.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = UserDao.getInstance();
//        update(userDao);
//        save(userDao);
//        delete(userDao);

        getAllUsers(userDao);
    }

    private static void getAllUsers(UserDao userDao) {
        List<User> allUsers = userDao.findAll();
        allUsers.forEach(user -> System.out.println(user + System.lineSeparator()));
    }

    private static void delete(UserDao userDao) {
        var delete = userDao.delete(7);
        System.out.println(delete);
    }

    private static void save(UserDao userDao) {
        var newUser = userDao.save(new User(1, "Ilia", "11111", "zharinov@mail.ru"));
        System.out.println(newUser);
    }

    private static void update(UserDao userDao) {
        var maybeUser = userDao.findById(1);
        System.out.println(maybeUser);
        maybeUser.ifPresent(user -> {
            user.setEmail("Ivanov@mail.ru");
            userDao.update(user);
        });
    }
}
