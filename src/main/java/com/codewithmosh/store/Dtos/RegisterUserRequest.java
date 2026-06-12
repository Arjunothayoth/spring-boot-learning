package com.codewithmosh.store.Dtos;

import lombok.Data;

@Data //date user for getter setter and toString
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
}
