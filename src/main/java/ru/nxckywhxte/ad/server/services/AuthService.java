package ru.nxckywhxte.ad.server.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthUserDto;
import ru.nxckywhxte.ad.server.dtos.profile.CreateUserProfileDto;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;

import java.io.IOException;

public interface AuthService {
    ResponseEntity<?> register(CreateUserDto createUserDto, CreateUserProfileDto createUserProfileDto, MultipartFile avatarFile);

    ResponseEntity<?> login(LoginAuthUserDto loginAuthUserDto);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response
    ) throws IOException;
}
