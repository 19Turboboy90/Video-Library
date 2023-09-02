package main.java.ru.video.library.exception;

public class DaoException extends RuntimeException{
    public DaoException(Throwable throwable){
        super(throwable);
    }
}
