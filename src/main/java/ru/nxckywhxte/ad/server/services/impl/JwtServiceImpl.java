package ru.nxckywhxte.ad.server.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.nxckywhxte.ad.server.services.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${application.security.access-token.secret}")
    private String secret;
    @Value("${application.security.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${application.security.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


//    @Value("${jwt.secret}")
//    private String secret;
//    @Value("${jwt.lifetime}")
//    private Duration jwtLifetime;
//
//    @Override
//    // Вместо стандратного UserDetails можно использовать нашего пользователя
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        List<String> roleList = userDetails
//                .getAuthorities()
//                .stream()
//                .map(role -> String.valueOf(new SimpleGrantedAuthority(role.getName())))
//                .toList();
//        claims.put("roles", roleList);
//        claims.put("username", userDetails.getUsername());
//        // Можно положить email нашего пользователя
//        // claims.put("email", email);
//        // Дата создания токена
//        Date issuedDate = new Date();
//        // Дата истечения срока токена
//        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        Key key = decodeKey(secret);
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(String.valueOf(userDetails.getId()))
//                .setIssuedAt(issuedDate)
//                .setExpiration(expiredDate)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    @Override
//    public String getUsernameFromToken(String token) {
//        return getAllClaimsFromToken(token).getSubject();
//    }
//
//    @Override
//    public List getRolesFromToken(String token) {
//        return getAllClaimsFromToken(token).get("roles",List.class);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(decodeKey(secret))
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key decodeKey(String secret) {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}
