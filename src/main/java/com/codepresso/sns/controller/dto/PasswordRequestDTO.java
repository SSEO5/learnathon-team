package com.codepresso.sns.controller.dto;

import javax.validation.constraints.NotBlank;

public record PasswordRequestDTO(@NotBlank String currentPassword, @NotBlank String newPassword) {
}
