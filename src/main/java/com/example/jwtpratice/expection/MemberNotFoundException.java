package com.example.jwtpratice.expection;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message) {
        super(message);
    }


    public MemberNotFoundException() {
        super("사용자를 찾을 수 없습니다.");

    }
}
