package ru.nxckywhxte.ad.server.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.nxckywhxte.ad.server.entities.User;

import java.io.IOException;

public interface TokenService {
    void saveUserToken(User user, String accessToken);

    void revokeAllUserToken(User user);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response
    ) throws IOException;
}
