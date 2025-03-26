package io.jeidiiy.sirenordersystem.user.domain.dto;

import io.jeidiiy.sirenordersystem.web.validation.Password;

public record UserPasswordPatchRequestBody(
    @Password String oldPassword, @Password String newPassword) {}
