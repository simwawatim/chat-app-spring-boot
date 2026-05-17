package com.example.chat.user.DTOs;

import lombok.Data;

@Data
public class UserPatchDTO {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}