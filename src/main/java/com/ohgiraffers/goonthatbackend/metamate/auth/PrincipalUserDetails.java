package com.ohgiraffers.goonthatbackend.metamate.auth;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class PrincipalUserDetails implements UserDetails {

    private final MetaUser metaUser;

    public PrincipalUserDetails(MetaUser metaUser) {
        this.metaUser = metaUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(metaUser.getRole().getValue()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return metaUser.getPassword();
    }

    @Override
    public String getUsername() {
        return metaUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
