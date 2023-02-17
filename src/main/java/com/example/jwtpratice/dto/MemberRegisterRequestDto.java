package com.example.jwtpratice.dto;


import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor //필드에 쓴 모든생성자만 만들어줌
@NoArgsConstructor(access = AccessLevel.PROTECTED) //파라미터가 없는 기본 생성자를 만들어줌
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
