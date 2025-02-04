package ru.zharinov.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPath {
    public static final String LOGIN = "/login";
    public static final String MOVIES = "/movies";
    public static final String MOVIE = "/movie";
    public static final String ACTOR = "/actor";
    public static final String DIRECTOR = "/director";
    public static final String LOGOUT = "/logout";
    public static final String REGISTRATION = "/registration";
    public static final String ADMIN_PAGE = "/admin";
    public static final String SAVE_DIRECTOR = "/admin/add-director";
    public static final String SAVE_ACTOR = "/admin/add-actor";
    public static final String SAVE_MOVIE = "/admin/add-movie";
    public static final String ADMIN_INFO_MOVIES = "/admin/movies-page";
    public static final String ADMIN_INFO_ACTORS = "/admin/actors-page";
    public static final String ADMIN_INFO_DIRECTORS = "/admin/directors-page";
    public static final String ADMIN_INFO_USERS = "/admin/users-page";
    public static final String ADMIN_DELETE_ACTOR = "/admin/actor-delete";
    public static final String ADMIN_DELETE_DIRECTOR = "/admin/director-delete";
}
