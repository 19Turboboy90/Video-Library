package main.java.ru.video.library.entity;

import java.time.LocalDate;

public class Director {
    private Integer id;
    private String name;
    private LocalDate dateOfBirth;

    public Director(Integer id, String name, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Director() {
    }
}
