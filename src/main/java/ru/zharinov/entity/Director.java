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
public class Director {
    private Integer id;
    private String name;
    private LocalDate dateOfBirthday;
    private List<Movie> movies;
}
