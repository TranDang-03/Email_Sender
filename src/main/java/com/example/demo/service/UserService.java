package com.example.demo.service;

import com.example.demo.model.UserModel;
import com.example.demo.resgistration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserModel> getUser();

    UserModel registerUser(RegistrationRequest request);

    void UserVerificationToken(UserModel user, String verificationToken);

    String validateToken(String theToken);

    Optional<UserModel> findByEmail(String email);
}
