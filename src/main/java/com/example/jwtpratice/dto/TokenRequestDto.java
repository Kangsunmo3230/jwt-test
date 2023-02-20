package com.example.jwtpratice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성해준다
//@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 만들어준다.
public class TokenRequestDto {

    private String accessToken;
    private String refreshToken;

    @Builder
    private TokenRequestDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }
}
