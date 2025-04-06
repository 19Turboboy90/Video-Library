package ru.zharinov.dao;

import lombok.SneakyThrows;
import ru.zharinov.entity.Role;
import ru.zharinov.entity.User;
import ru.zharinov.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Integer, User> {

    private static final String FIND_ALL_USERS = """
            SELECT u.id, u.name, u.email, u.password, u.role
            FROM users u
            """;

    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = FIND_ALL_USERS + """
            WHERE u.email = ? AND u.password = ?;
            """;

    private static final String SAVE_USER = """
            INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?);
            """;

    private static final String FIND_USER_BY_ID = FIND_ALL_USERS + """
            WHERE u.id = ?;
            """;

    private static final String FIND_USERS_BY_PREFIX = FIND_ALL_USERS + """
            WHERE u.name LIKE ?;
            """;

    private static final String DELETE_USER = """
            DELETE FROM users
            WHERE users.id = ?
            """;

    private static final String UPDATE_USER = """
            UPDATE users  SET name = ?, email = ?, password = ?, role = ? WHERE id = ?;
            """;

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @SneakyThrows
    public List<User> findAllUsersByPrefix(String prefix) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_USERS_BY_PREFIX)) {
            preparedStatement.setObject(1, prefix + "%");
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findById(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_USER_BY_ID)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    @SneakyThrows
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement =
                     connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setObject(1, email);
            preparedStatement.setObject(2, password);
            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }


    @Override
    @SneakyThrows
    public User save(User entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getEmail());
            preparedStatement.setObject(3, entity.getPassword());
            preparedStatement.setObject(4, entity.getRole().name());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
            return entity;
        }
    }

    @Override
    @SneakyThrows
    public void update(User entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getEmail());
            preparedStatement.setObject(3, entity.getPassword());
            preparedStatement.setObject(4, entity.getRole().name());
            preparedStatement.setObject(5, entity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {

        return User.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .role(Role.valueOf(resultSet.getObject("role", String.class)))
                .build();
    }
}
