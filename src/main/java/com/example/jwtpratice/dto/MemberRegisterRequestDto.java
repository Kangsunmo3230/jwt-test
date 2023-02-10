package com.example.jwtpratice.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class MemberRegisterRequestDto {

    private String Email;
    private String Password;
    private Long id;

    @Builder
    public MemberRegisterRequestDto(Long id, String Email) {
        this.id = id;
        this.Email = Email;
    }

}
