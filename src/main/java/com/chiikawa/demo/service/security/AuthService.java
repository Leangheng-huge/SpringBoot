package com.chiikawa.demo.service.security;

import com.chiikawa.demo.DTO.User.UserDto;
import com.chiikawa.demo.DTO.auth.AuthDto;
import com.chiikawa.demo.DTO.auth.AuthResponseDto;
import com.chiikawa.demo.DTO.auth.RefreshTokenDto;
import com.chiikawa.demo.DTO.auth.RefreshTokenResponseDto;
import com.chiikawa.demo.exception.model.CustomAuthenticationException;
import com.chiikawa.demo.exception.model.DuplicateResourceException;
import com.chiikawa.demo.Mapper.UserMapper;

import com.chiikawa.demo.entity.RefreshToken;
import com.chiikawa.demo.entity.User;
import com.chiikawa.demo.repository.UserRepository;
import com.chiikawa.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;


@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthResponseDto register(UserDto payload) {
        // validate if username is existed
        if(userRepository.existsByName(payload.getName())) {
            throw new DuplicateResourceException("username is already existed");
        }

        // validate if email is existed
        if(userRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateResourceException("email is already existed");
        }

        User user = mapper.toEntity(payload);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.save(user);
        String accessToken = jwtUtil.generateToken(createdUser);

        // generate refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(createdUser);

        return new AuthResponseDto(accessToken,refreshToken.getToken());
    }

    public AuthResponseDto login(AuthDto payload) {
        try {
            // login user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(payload.getUsername(),payload.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new CustomAuthenticationException("invalid username or password");
        }

        UserDetails userDetails = userService.loadUserByUsername(payload.getUsername());
        String accessToken = jwtUtil.generateToken(userDetails);

        // generate refresh token
        User user = (User) userDetails;
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponseDto(accessToken,refreshToken.getToken());
    }

    public RefreshTokenResponseDto refreshToken(RefreshTokenDto payload)  {
        String token = payload.getRefreshToken();

        // find by token
        RefreshToken refreshToken = refreshTokenService.findByToken(token);
        try {
            refreshToken = refreshTokenService.verifyToken(refreshToken);
        }catch (AuthenticationException e) {
            throw new CustomAuthenticationException("invalid refresh token");
        }

        // get user from refresh token
        User user = refreshToken.getUser();

        // generate new access token
        String newAccessToken = jwtUtil.generateToken(user);

        // rotate refresh token
        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(refreshToken);

        return new RefreshTokenResponseDto(newAccessToken,newRefreshToken.getToken(),"Bearer");
    }
}