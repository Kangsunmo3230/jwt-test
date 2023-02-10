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


//    @ElementCollection(fetch = FetchType.LAZY)
//    @Enumerated(EnumType.STRING)
//    private List<Role> roles = new ArrayList<>();


    @Builder
    public MemberDto(String email , String password, List<Role> roles){
        this.email = email;
        this.password = password;
//        this.roles = Collections.singletonList(Role.ROLE_MEMBER);
    }
//
//    public void addRole(Role role) {
//        this.roles.add(role);
//    }
}
