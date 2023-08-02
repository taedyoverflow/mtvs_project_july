package com.ohgiraffers.goonthatbackend.metamate.admin;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class RoleManagementService {

    private final MetaUserRepository metaUserRepository;

    @Transactional
    public Optional<MetaUser> searchMetaUser(String email) {
        return metaUserRepository.findByEmail(email);
    }

    public void update(RoleManagementDto roleManagementDto) {

        MetaUser metaUser = searchMetaUser(roleManagementDto.getEmail()).orElse(null);

        if (metaUser != null) {
            metaUser.adminUpdate(
                    roleManagementDto.getEmail(),
                    roleManagementDto.getRole(),
                    roleManagementDto.getName(),
                    roleManagementDto.getNickname());
        }
    }

    @Transactional(readOnly = true)
    public List<RoleManagementDto> users() {
        return metaUserRepository.findAll().stream()
                .map(RoleManagementDto::fromEntity).collect(Collectors.toList());
    }

    public void deleteUser(String email) {
        metaUserRepository.deleteByEmail(email);
    }

}