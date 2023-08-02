package com.ohgiraffers.goonthatbackend.metamate.auth;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PrincipalUserDetailsService implements UserDetailsService {

    private final MetaUserRepository metaUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MetaUser metaUser = metaUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new PrincipalUserDetails(metaUser);
    }
}
