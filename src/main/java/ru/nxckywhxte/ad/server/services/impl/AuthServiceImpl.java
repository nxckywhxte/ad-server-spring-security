package ru.nxckywhxte.ad.server.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthResponse;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthUserDto;
import ru.nxckywhxte.ad.server.dtos.auth.RegisterAuthResponse;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.services.AuthService;

import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;
    private final TokenServiceImpl tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterAuthResponse register(CreateUserDto createUserDto) {
        User newUser = this.userService.createNewUser(createUserDto);
        return RegisterAuthResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .roles(newUser.getRoles())
                .groups(newUser.getGroups())
                .build();
    }

    @Override
    public LoginAuthResponse login(LoginAuthUserDto loginAuthUserDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginAuthUserDto.getUsername(),
                            loginAuthUserDto.getRawPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(UNAUTHORIZED, "Неверные данные для входа");
        }
        User user = userService.loadUserByUsername(loginAuthUserDto.getUsername());

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.saveUserToken(user, accessToken);
        return LoginAuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .groups(user.getGroups())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenService.refreshToken(request, response);
    }


}
