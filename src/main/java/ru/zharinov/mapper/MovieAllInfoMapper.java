package ru.zharinov.mapper;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.MovieAllInfoDto;
import ru.zharinov.entity.Movie;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MovieAllInfoMapper implements Mapper<Movie, MovieAllInfoDto> {
    private static final MovieAllInfoMapper INSTANCE = new MovieAllInfoMapper();

    private final ActorMapper actorMapper = ActorMapper.getInstance();
    private final DirectorMapper directorMapper = DirectorMapper.getInstance();

    @Override
    public MovieAllInfoDto mapper(Movie object) {
        return MovieAllInfoDto.builder()
                .id(object.getId())
                .name(object.getName())
                .premierDate(object.getPremierDate())
                .country(object.getCountry())
                .genre(object.getGenre())
                .director(directorMapper.mapper(object.getDirector()))
                .actors(object.getActors().stream().map(actorMapper::mapper).toList())
                .build();
    }

    public static MovieAllInfoMapper getInstance() {
        return INSTANCE;
    }
}
