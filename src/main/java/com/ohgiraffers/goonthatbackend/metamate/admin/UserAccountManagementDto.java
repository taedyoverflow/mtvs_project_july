package com.ohgiraffers.goonthatbackend.metamate.admin;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserAccountManagementDto {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String number;
    private String major;
    private Role role;
    private LocalDateTime createdAt;

    public static UserAccountManagementDto fromEntity(MetaUser metaUser) {
        return new UserAccountManagementDto(
                metaUser.getId(),
                metaUser.getEmail(),
                metaUser.getName(),
                metaUser.getNickname(),
                metaUser.getNumber(),
                metaUser.getMajor(),
                metaUser.getRole(),
                metaUser.getCreatedAt()
        );
    }
}