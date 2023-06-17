package ru.nxckywhxte.ad.server.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.nxckywhxte.ad.server.entities.Token;
import ru.nxckywhxte.ad.server.repositories.TokenRepository;
import ru.nxckywhxte.ad.server.services.LogoutService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            return;
        }
        accessToken = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(accessToken)
                .orElse(null);
        if (Objects.isNull(storedToken)) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();

        }
    }
}
