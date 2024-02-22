package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SignUpRequestDTO {
    String userName;
    String email;
    String password;
    String introduction;
    String occupation;
    Date birthday;
    String city;


}
