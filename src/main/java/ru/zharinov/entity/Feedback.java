package ru.zharinov.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Feedback {
    private Integer id;
    private String text;
    private Integer assessment;
    private Movie movie;
    @ToString.Exclude
    private User user;
}
