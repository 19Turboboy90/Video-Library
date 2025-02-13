package ru.zharinov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Feedback {
    private Integer id;
    private String text;
    private Integer assessment;
    private Movie movie;
    private User user;
}
