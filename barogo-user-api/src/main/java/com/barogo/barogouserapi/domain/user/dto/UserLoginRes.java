package com.barogo.barogouserapi.domain.user.dto;

public record UserLoginRes(
    String userId,
    String loginId,
    String accessToken,
    String refreshToken
) {
}
