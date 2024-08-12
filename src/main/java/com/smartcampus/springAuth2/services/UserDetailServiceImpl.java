package com.smartcampus.springAuth2.services;
import com.smartcampus.springAuth2.components.JwtUtil;
import com.smartcampus.springAuth2.dtos.JwtUserDto;
import com.smartcampus.springAuth2.dtos.UserDetailDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        JwtUserDto userDto = jwtUtil.getUser(token);
        UserDetailDto user = new UserDetailDto();
        user.setUserId(userDto.getUserId());
        user.setSessionId(userDto.getSessionId());
        user.setUsername(userDto.getUsername());
        user.setPegeId(userDto.getPegeId());
        user.setIsActive(userDto.getIsActive());
        user.setAuthorities(this.grantedAuthorities(userDto.getRoles()));
        return user;
    }

    private List<GrantedAuthority> grantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());

        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }
}
