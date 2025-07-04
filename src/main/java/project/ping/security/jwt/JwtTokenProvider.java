package project.ping.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.ping.dto.JwtDTO;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider implements InitializingBean {

    @Value("${spring.jwt.secret}")
    private String secret;

    private SecretKey secretKey;

    @Value("${spring.jwt.token.access_expiration}")
    private Long accessTokenExpiration;

    @Value("${spring.jwt.token.refresh_expiration}")
    private Long refreshTokenExpiration;

    // SecretKey 생성하기
    @Override
    public void afterPropertiesSet() throws Exception {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 로그인 시, 액세스 토큰과 리프레시 토큰을 발급
    public JwtDTO createToken(Long memberId, String email){
        Date now = new Date();

        String accessToken = Jwts.builder()
                .claim("memberId", memberId)
                .claim("email", email)
                .claim("token-type", "access-token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration))
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("memberId", memberId)
                .claim("email", email)
                .claim("token-type", "refresh-token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiration))
                .signWith(secretKey)
                .compact();


;       return JwtDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
