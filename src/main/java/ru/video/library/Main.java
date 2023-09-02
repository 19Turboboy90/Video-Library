package main.java.ru.video.library;

import main.java.ru.video.library.dao.MovieDao;

public class Main {
    public static void main(String[] args) {
        MovieDao movieDao = new MovieDao();
        movieDao.delete(10);
    }
}
