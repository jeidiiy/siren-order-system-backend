package io.jeidiiy.sirenordersystem.jwt.model;

import lombok.Builder;

@Builder
public record JwtToken(String accessToken, String refreshToken) {}
