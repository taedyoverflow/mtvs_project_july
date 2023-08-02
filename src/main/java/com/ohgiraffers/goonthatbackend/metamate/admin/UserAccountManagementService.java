package com.ohgiraffers.goonthatbackend.metamate.admin;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserAccountManagementService {

    private final MetaUserRepository metaUserRepository;

    @Transactional(readOnly = true)
    public Optional<UserAccountManagementDto> getUserAccount(Long id) {
        return metaUserRepository.findById(id)
                .map(UserAccountManagementDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<UserAccountManagementDto> getUserAccounts() {
        return metaUserRepository.findAll().stream()
                .map(UserAccountManagementDto::fromEntity).collect(Collectors.toList());
    }

    public void deleteUserAccount(Long id) {
        metaUserRepository.deleteById(id);
    }

}