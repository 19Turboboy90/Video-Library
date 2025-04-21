package ru.zharinov.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Role role;
    @ToString.Exclude
    private List<Feedback> feedbacks;
}
