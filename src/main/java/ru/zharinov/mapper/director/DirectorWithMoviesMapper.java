package ru.zharinov.mapper.director;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.director.DirectorWithMoviesDto;
import ru.zharinov.entity.Director;
import ru.zharinov.mapper.Mapper;
import ru.zharinov.mapper.movie.MovieMapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorWithMoviesMapper implements Mapper<Director, DirectorWithMoviesDto> {
    private static final DirectorWithMoviesMapper INSTANCE = new DirectorWithMoviesMapper();
    private final MovieMapper movieMapper = MovieMapper.getInstance();

    @Override
    public DirectorWithMoviesDto mapper(Director object) {
        return DirectorWithMoviesDto.builder()
                .id(object.getId())
                .name(object.getName())
                .dateOfBirthday(object.getDateOfBirthday())
                .movies(object.getMovies().stream().map(movieMapper::mapper).toList())
                .build();
    }

    public static DirectorWithMoviesMapper getInstance() {
        return INSTANCE;
    }
}
