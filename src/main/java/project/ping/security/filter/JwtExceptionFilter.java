package project.ping.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.ping.apiPayload.ApiResponse;
import project.ping.apiPayload.exception.GeneralException;
import project.ping.apiPayload.status.ErrorStatus;

import java.io.IOException;

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (GeneralException ge) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, ge.getErrorStatus());
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest req,
                                 HttpServletResponse res, ErrorStatus e) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.onFailure(e, null);
        res.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

}
