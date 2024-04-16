package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.UserModel;
import com.example.demo.model.VerificationToken;
import com.example.demo.resgistration.RegistrationRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<UserModel> getUser() {

        return userRepository.findAll();
    }

    @Override
    public UserModel registerUser(RegistrationRequest request) {

        Optional<UserModel> user = this.userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            throw new UserAlreadyExistsException(
                    "Email" + request.getEmail() + "already exists!!!"
            );
        }

        var newUser = new UserModel();
        newUser.setEmail(request.getEmail());
        newUser.setFirtName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPassword(request.getPassword());
        newUser.setRole(request.getRole());
        return userRepository.save(newUser);
    }

    @Override
    public void UserVerificationToken(UserModel user, String verificationToken) {
        var token  = new VerificationToken(verificationToken, user);
        verificationTokenRepository.save(token);

    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = verificationTokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }

        UserModel user = token.getUserModel();
        Calendar calendar = Calendar.getInstance();

        if((token.getExprirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(token);
            return "Token already expired!!!";
        }

        user.setEnable(true);
        userRepository.save(user);
        return "User valid";
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
