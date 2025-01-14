package uz.nemo.hotelmanagementsystem.service;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class JwtBlackListService {

    private final JwtService jwtService;
    private final Map<String, Long> tokenBlacklist = new ConcurrentHashMap<>();

    public boolean blackListToken(String token) {
        long expiration = getExpirationTime(token);

        if (expiration > 0 ){
            tokenBlacklist.put(token, System.currentTimeMillis() + expiration);
            return true;
        }
        return false;
    }

    public boolean isTokenBlacklisted(String token) {
        Long expirationTime = tokenBlacklist.get(token);
        if (expirationTime != null) {
            if (System.currentTimeMillis() < expirationTime) {
                return true;
            } else {
                tokenBlacklist.remove(token);
            }
        }
        return false;
    }

    public long getExpirationTime(String token) {
        Claims claims = jwtService.extractAllClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.getTime() - System.currentTimeMillis();
    }
}
