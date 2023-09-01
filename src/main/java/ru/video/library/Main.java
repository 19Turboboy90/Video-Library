package main.java.ru.video.library;

import main.java.ru.video.library.sql.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Main {
    static String sql = """
            SELECT * FROM movie;
            """;

    public static void main(String[] args) throws SQLException {
        extracted();

    }

    private static void extracted() throws SQLException {
        String sql = """
                SELECT name, premiere_date, country, genre 
                FROM movie
                WHERE premiere_date < ?;
                """;


        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDate.of(1999,1,1).atStartOfDay()));
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        }
    }
}
