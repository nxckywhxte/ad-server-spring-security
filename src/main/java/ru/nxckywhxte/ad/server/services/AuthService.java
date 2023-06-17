package ru.nxckywhxte.ad.server.services;

import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthResponse;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthUserDto;
import ru.nxckywhxte.ad.server.dtos.auth.RegisterAuthResponse;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;

public interface AuthService {
    RegisterAuthResponse register(CreateUserDto createUserDto) throws ResponseStatusException;

    LoginAuthResponse login(LoginAuthUserDto loginAuthUserDto);
}
