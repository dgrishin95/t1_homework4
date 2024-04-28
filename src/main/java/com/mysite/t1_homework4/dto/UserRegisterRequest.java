package com.mysite.t1_homework4.dto;

import com.mysite.t1_homework4.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}
