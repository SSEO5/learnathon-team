package com.codepresso.sns.controller.dto;

import com.codepresso.sns.vo.User;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record LoginRequestDTO(@Email @NotBlank String email, @NotBlank String password) {
}
