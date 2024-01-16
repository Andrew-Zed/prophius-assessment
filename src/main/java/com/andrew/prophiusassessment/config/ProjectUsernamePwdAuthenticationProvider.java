package com.andrew.prophiusassessment.config;

import com.andrew.prophiusassessment.entity.Authority;
import com.andrew.prophiusassessment.entity.User;
import com.andrew.prophiusassessment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ProjectUsernamePwdAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Optional<User> userList = userRepository.findByEmail(username);

        if (userList.isPresent() && passwordEncoder.matches(pwd, userList.get().getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(userList.get().getAuthorities()));
        }
//        if (userList.isPresent()) {
//            return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(userList.get().getAuthorities()));
//        }
        return null;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
