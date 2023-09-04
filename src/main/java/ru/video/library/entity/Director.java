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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Director{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", dateOfBirth=" + dateOfBirth +
               '}';
    }
}
