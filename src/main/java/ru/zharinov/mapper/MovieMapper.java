package ru.zharinov.mapper;

import lombok.experimental.UtilityClass;
import ru.zharinov.dto.movie.CreateMovieDto;
import ru.zharinov.dto.movie.MovieAllInfoDto;
import ru.zharinov.dto.movie.MovieInfoDto;
import ru.zharinov.entity.Movie;
import ru.zharinov.util.DateFormatter;

@UtilityClass
public class MovieMapper {
    public static MovieInfoDto toMovieInfoDto(Movie object) {
        return MovieInfoDto.builder()
                .id(object.getId())
                .description("""
                        %s, %s
                        """.formatted(object.getName(), object.getPremierDate()))
                .build();
    }

    public static MovieAllInfoDto toMovieAllInfoDto(Movie object) {
        return MovieAllInfoDto.builder()
                .id(object.getId())
                .name(object.getName())
                .premierDate(object.getPremierDate())
                .country(object.getCountry())
                .genre(object.getGenre())
                .director(DirectorMapper.toDirectorDto(object.getDirector()))
                .actors(object.getActors().stream().map(ActorMapper::toActorDto).toList())
                .feedbacks(object.getFeedbacks().stream().map(FeedbackMapper::toFeedbackDto).toList())
                .build();
    }

    public static Movie toMovie(CreateMovieDto object) {
        return Movie.builder()
                .id(object.getId().isEmpty() ? null : Integer.parseInt(object.getId()))
                .name(object.getName())
                .premierDate(DateFormatter.format(object.getPremiereDate()))
                .country(object.getCountry())
                .genre(object.getGenre())
                .build();
    }
}
