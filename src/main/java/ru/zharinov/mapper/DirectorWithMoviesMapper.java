package ru.zharinov.mapper;

import lombok.NoArgsConstructor;
import ru.zharinov.dto.DirectorDto;
import ru.zharinov.entity.Director;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DirectorWithMoviesMapper implements Mapper<Director, DirectorDto> {
    private static final DirectorWithMoviesMapper INSTANCE = new DirectorWithMoviesMapper();
    private final MovieMapper movieMapper = MovieMapper.getInstance();

    @Override
    public DirectorDto mapper(Director object) {
        return DirectorDto.builder()
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
