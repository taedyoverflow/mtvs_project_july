package com.ohgiraffers.goonthatbackend.metamate.service;

import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.EditRequestDto;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.JoinRequestDto;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MetaUserService  {

    private final MetaUserRepository metaUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public boolean hasEmail(String email) {
        return metaUserRepository.existsByEmail(email);
    }

    public boolean hasNickname(String nickname) {
        return metaUserRepository.existsByNickname(nickname);
    }

    // 회원가입
    public Long join(JoinRequestDto metaUserJoinDto) {
        metaUserJoinDto.encodePassword(passwordEncoder.encode(metaUserJoinDto.getPassword()));
        return metaUserRepository.save(metaUserJoinDto.toEntity()).getId();
    }

    @Transactional
    public void edit(EditRequestDto editRequestDto, SessionMetaUser sessionMetaUser) {
        MetaUser metaUser = metaUserRepository.findByEmail(sessionMetaUser.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String encodePw = passwordEncoder.encode(editRequestDto.getPassword());
        metaUser.update(editRequestDto.getNickname(), encodePw);

        // Security 세션 변경 처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        metaUser.getEmail(), editRequestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}