package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.constant.SecurityConstants;
import com.andrew.prophiusassessment.dto.UserLoginDto;
import com.andrew.prophiusassessment.dto.UserLoginResponseDto;
import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.entity.User;
import com.andrew.prophiusassessment.exceptions.UserAlreadyExistException;
import com.andrew.prophiusassessment.exceptions.UserNotFoundException;
import com.andrew.prophiusassessment.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDto registrationDto) {
//        if (emailExists(registrationDto.getEmail())) {
//            throw new UserAlreadyExistException("There is an account with that email address: " + registrationDto.getEmail());
//        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("A user with the email " + registrationDto.getEmail() + " already exists.");
        }
        String hashPwd = passwordEncoder.encode(registrationDto.getPassword());
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setRole("USER");
        user.setEmail(registrationDto.getEmail().toLowerCase());
        user.setPassword(hashPwd);

        return userRepository.save(user);
    }

    public UserLoginResponseDto loginUser(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginDto.getEmail()));

        if(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String jwtToken = generateJwtToken(user);
//            return new UserLoginResponseDto(user.getId(), user.getUsername(), user.getEmail(), jwtToken);
            return new UserLoginResponseDto(user.getId(), user.getUsername(), user.getEmail(), jwtToken);

        } else {
            throw new UserNotFoundException("Invalid credentials provided.");
        }
    }

    private String generateJwtToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .setIssuer("Prophius")
                .setSubject("JWT Token")
                .claim("username", user.getUsername())
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 30000000)) // Set appropriate expiration
                .signWith(key)
                .compact();
        return jwt;
    }


    private boolean emailExists(String email) {
       return userRepository.findByEmail(email).isPresent();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
