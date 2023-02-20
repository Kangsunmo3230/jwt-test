package com.example.jwtpratice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성해준다
public class TokenResponseDto {

    private String refreshToken;
    private String accessToken;

    @Builder
    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
