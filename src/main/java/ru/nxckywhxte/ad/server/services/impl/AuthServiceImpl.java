package ru.nxckywhxte.ad.server.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthResponse;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthUserDto;
import ru.nxckywhxte.ad.server.dtos.auth.RegisterAuthResponse;
import ru.nxckywhxte.ad.server.dtos.auth.ResponseAuthDto;
import ru.nxckywhxte.ad.server.dtos.profile.CreateUserProfileDto;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;
import ru.nxckywhxte.ad.server.entities.Profile;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.services.AuthService;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;
    private final TokenServiceImpl tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserProfileServiceImpl userProfileService;
    private final StorageServiceImpl storageService;

    @Override
    public ResponseEntity<?> register(CreateUserDto createUserDto, CreateUserProfileDto createUserProfileDto, MultipartFile avatarFile) {
        // Создаем нового пользователя
        User newUser = this.userService.createNewUser(createUserDto);
        this.storageService.init();
        this.storageService.initIconsPath();
        // Загружаем файл аватара на сервер
        String avatarUri = this.storageService.saveIcon(avatarFile, newUser.getId());

        //Создаем новый профиль
        Profile newProfile = this.userProfileService.createNewProfile(newUser.getId(), createUserProfileDto, avatarUri);
        return new ResponseEntity<>(RegisterAuthResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .roles(newUser.getRoles())
                .groups(newUser.getGroups())
                .profile(newProfile)
                .build(), OK);
    }

    @Override
    public ResponseEntity<?> login(LoginAuthUserDto loginAuthUserDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginAuthUserDto.getUsername(),
                            loginAuthUserDto.getRawPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    ResponseAuthDto
                            .builder()
                            .message("Неверные данные для входа")
                            .status(UNAUTHORIZED.value())
                            .build(),
                    UNAUTHORIZED
            );
        }
        User user = userService.loadUserByUsername(loginAuthUserDto.getUsername());

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.revokeAllUserToken(user);
        tokenService.saveUserToken(user, accessToken);
        return new ResponseEntity<>(LoginAuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .groups(user.getGroups())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build(), OK);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenService.refreshToken(request, response);
    }


}
