package ru.nxckywhxte.ad.server.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthResponse;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthUserDto;
import ru.nxckywhxte.ad.server.dtos.auth.RegisterAuthResponse;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;

import java.io.IOException;

public interface AuthService {
    RegisterAuthResponse register(CreateUserDto createUserDto) throws ResponseStatusException;

    LoginAuthResponse login(LoginAuthUserDto loginAuthUserDto);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response
    ) throws IOException;
}
