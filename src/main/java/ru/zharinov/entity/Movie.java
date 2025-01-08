package ru.zharinov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Movie {
    private Integer id;
    private String name;
    private LocalDate premierDate;
    private String country;
    private String genre;
    private Director director;
    private List<Actor> actors;
}
