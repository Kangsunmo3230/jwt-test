package com.example.jwtpratice.expection;

public class InvalidRefreshTokenException extends  RuntimeException{
    public InvalidRefreshTokenException(String message){
        super(message);
    }

    public InvalidRefreshTokenException(){
        super("refreshToken 만료");
    }
}
