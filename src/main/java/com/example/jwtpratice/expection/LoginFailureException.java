package com.example.jwtpratice.expection;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }

    public LoginFailureException(){
        super("로그인에 실패했습니다");
    }
}
