package ru.zharinov.mapper.movie;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.movie.CreateMovieDto;
import ru.zharinov.entity.Movie;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.util.DateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrUpdateMovieMapper implements Mapper<CreateMovieDto, Movie> {
    private static final CreateOrUpdateMovieMapper INSTANCE = new CreateOrUpdateMovieMapper();

    @Override
    public Movie mapper(CreateMovieDto object) {
        return Movie.builder()
                .id(object.getId().isEmpty() ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .premierDate(DateFormatter.format(object.getPremiereDate()))
                .country(object.getCountry())
                .genre(object.getGenre())
                .build();
    }

    public static CreateOrUpdateMovieMapper getInstance() {
        return INSTANCE;
    }
}
