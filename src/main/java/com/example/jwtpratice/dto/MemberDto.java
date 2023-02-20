package com.example.jwtpratice.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    //해야할 것.
    // 다듣고
    private String refreshToken;
//    @ElementCollection(fetch = FetchType.LAZY)
//    @Enumerated(EnumType.STRING)
//    private List<Role> roles = new ArrayList<>();
    private String roles;


    @Builder
    public MemberDto(String email, String password,String refreshToken) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.refreshToken = refreshToken;
    }
   // public void addRole(Role role) {
  //      this.roles.add(role);
 //   }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}