package com.example.jwtpratice.controller;


import com.example.jwtpratice.dto.MemberLoginRequestDto;
import com.example.jwtpratice.dto.MemberRegisterRequestDto;
import com.example.jwtpratice.dto.TokenRequestDto;
import com.example.jwtpratice.dto.TokenResponseDto;
import com.example.jwtpratice.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;
    //private final ResponseService responseService;

    @PostMapping("/register")
    public ResponseEntity<MemberRegisterRequestDto> register(@RequestBody MemberRegisterRequestDto requestDto) {
        MemberRegisterRequestDto responseDto = signService.registerMember(requestDto);
        log.info("responseDto=> {}",responseDto);
        return new ResponseEntity<MemberRegisterRequestDto>(responseDto,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginRequestDto> login(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginRequestDto responseDto = signService.loginMember(requestDto);
        log.info("responseDto=> {}",responseDto);
        return new ResponseEntity<MemberLoginRequestDto>(responseDto,HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reIssue(@RequestBody TokenRequestDto tokenRequestDto) {
        log.info("tokenReuqeustDto => {}",tokenRequestDto);
        TokenResponseDto responseDto = signService.reIssue(tokenRequestDto);
        return new ResponseEntity<TokenResponseDto>(responseDto,HttpStatus.OK);
    }
}
