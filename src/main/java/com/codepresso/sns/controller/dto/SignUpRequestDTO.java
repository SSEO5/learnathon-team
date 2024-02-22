package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;


public record SignUpRequestDTO(@NotBlank String userName, @Email @NotBlank String email, @NotBlank String password, String introduction, String occupation, Date birthday, String city) {
}
