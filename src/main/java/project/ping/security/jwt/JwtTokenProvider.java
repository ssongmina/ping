package project.ping.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import project.ping.apiPayload.exception.GeneralException;
import project.ping.apiPayload.status.ErrorStatus;
import project.ping.dto.JwtDTO;
import project.ping.security.auth.MemberDetail;
import project.ping.security.auth.MemberDetailService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider implements InitializingBean {

    private final MemberDetailService memberDetailService;

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

    // 요청 헤더에서 토큰 뽑아내기
    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            return null;
        }
        return header.substring(7).trim();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new GeneralException(ErrorStatus.WRONG_TYPE_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new GeneralException(ErrorStatus.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new GeneralException(ErrorStatus.WRONG_TYPE_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new GeneralException(ErrorStatus.NOT_VALID_TOKEN);
        }
    }

    // 토큰을 복호화하여 claim 정보를 통해 인증 객체(Authentication) 생성
    public Authentication getAuthentication(String token) {
        MemberDetail memberDetail = memberDetailService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(memberDetail, null, memberDetail.getAuthorities());
    }

    // 토큰에서 이메일 추출
    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }

}
