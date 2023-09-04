package main.java.ru.video.library.entity;

import java.time.LocalDate;
import java.util.List;

public class Movie {
    private Integer id;
    private String name;
    private List<Actor> actors;
    private Director director;
    private LocalDate premiereDate;
    private String country;
    private String genre;

    public Movie(Integer id, String name, List<Actor> actors, Director director, LocalDate premiereDate, String country, String genre) {
        this.id = id;
        this.name = name;
        this.actors = actors;
        this.director = director;
        this.premiereDate = premiereDate;
        this.country = country;
        this.genre = genre;
    }

    public Movie() {
    }

    public void saveActor(Actor actor) {
        actors.add(actor);
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

    public List<Actor> getActors() {
        return List.copyOf(actors);
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
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
               ", actors=" + actors +
               ", director=" + director +
               ", premiereDate=" + premiereDate +
               ", country='" + country + '\'' +
               ", genre='" + genre + '\'' +
               '}';
    }
}
