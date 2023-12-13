package com.eventPlanner.models.dtos;

public class UserLoginDto {
    private final String name;
    private final String password;

    public UserLoginDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
