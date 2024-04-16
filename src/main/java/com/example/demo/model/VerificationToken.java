package com.example.demo.model;

import com.example.demo.model.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private Date exprirationTime;

    private static final Integer EXPIRATION_TIME = 15;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    public VerificationToken(String token, UserModel userModel){
        super();
        this.token = token;
        this.exprirationTime = this.getExprirationTime();
        this.userModel = userModel;
    }

    public VerificationToken(String token){
        super();
        this.token = token;
        this.exprirationTime = this.getExprirationTime();
    }

    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
