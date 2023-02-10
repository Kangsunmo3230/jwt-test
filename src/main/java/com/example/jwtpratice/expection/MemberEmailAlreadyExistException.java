package com.example.jwtpratice.expection;

public class MemberEmailAlreadyExistException extends RuntimeException {

    public MemberEmailAlreadyExistException(String message) {
        super(message);
    }


    public MemberEmailAlreadyExistException() {
        super("이미 등록된 유저입니다");

    }
}
