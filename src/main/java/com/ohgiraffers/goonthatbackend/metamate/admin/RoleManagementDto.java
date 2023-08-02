package com.ohgiraffers.goonthatbackend.metamate.admin;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.Role;
import lombok.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoleManagementDto {

    private String email;
    private Role role;
    private String name;
    private String nickname;


    public static RoleManagementDto fromEntity(MetaUser metaUser) {
        return new RoleManagementDto(
                metaUser.getEmail(),
                metaUser.getRole(),
                metaUser.getName(),
                metaUser.getNickname()
        );
    }
}
