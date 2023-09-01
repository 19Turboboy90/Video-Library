package main.java.ru.video.library.entity;

import java.time.LocalDate;

public class Movie {
    private Integer id;
    private String name;
    private LocalDate premiereDate;
    private String country;
    private String genre;

    public Movie(Integer id, String name, LocalDate premiereDate, String country, String genre) {
        this.id = id;
        this.name = name;
        this.premiereDate = premiereDate;
        this.country = country;
        this.genre = genre;
    }

    public Movie() {
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

    public LocalDate getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(LocalDate premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", premiereDate=" + premiereDate +
               ", country='" + country + '\'' +
               ", genre='" + genre + '\'' +
               '}';
    }
}
