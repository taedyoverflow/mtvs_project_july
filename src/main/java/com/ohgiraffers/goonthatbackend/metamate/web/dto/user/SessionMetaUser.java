package com.ohgiraffers.goonthatbackend.metamate.web.dto.user;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionMetaUser {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String number;
    private String major;
    private Role role;

    public static SessionMetaUser fromEntity(MetaUser metaUser) {
        return new SessionMetaUser(
                metaUser.getId(),
                metaUser.getEmail(),
                metaUser.getPassword(),
                metaUser.getName(),
                metaUser.getNickname(),
                metaUser.getNumber(),
                metaUser.getMajor(),
                metaUser.getRole()
        );
    }
}