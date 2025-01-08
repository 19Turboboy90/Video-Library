package ru.zharinov.mapper;

public interface Mapper<F, T> {
    T mapper(F object);
}
