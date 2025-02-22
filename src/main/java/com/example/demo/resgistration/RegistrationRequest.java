package com.example.demo.resgistration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private String password;

}
