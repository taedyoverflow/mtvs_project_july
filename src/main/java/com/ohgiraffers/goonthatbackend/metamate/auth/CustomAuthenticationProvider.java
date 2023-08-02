package com.ohgiraffers.goonthatbackend.metamate.auth;

import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PrincipalUserDetailsService principalUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        PrincipalUserDetails userDetails =
                (PrincipalUserDetails) principalUserDetailsService.loadUserByUsername(username);

        if(!this.passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        httpSession.setAttribute("user", SessionMetaUser.fromEntity(userDetails.getMetaUser()));

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails, userDetails.getAuthorities());
    }

    // 커스터마이징한 Authentication Token을 사용하지 않으므로 supports true
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
