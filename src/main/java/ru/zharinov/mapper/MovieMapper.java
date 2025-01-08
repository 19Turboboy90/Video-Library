package ru.zharinov.mapper;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.MovieInfoDto;
import ru.zharinov.entity.Movie;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MovieMapper implements Mapper<Movie, MovieInfoDto> {
    private static final MovieMapper INSTANCE = new MovieMapper();
    private final ActorMapper actorMapper = ActorMapper.getInstance();
    private final DirectorMapper directorMapper = DirectorMapper.getInstance();

    @Override
    public MovieInfoDto mapper(Movie object) {
        return MovieInfoDto.builder()
                .id(object.getId())
                .description("""
                        %s, %s
                        """.formatted(object.getName(), object.getPremierDate()))
                .build();
    }

    public static MovieMapper getInstance() {
        return INSTANCE;
    }
}
