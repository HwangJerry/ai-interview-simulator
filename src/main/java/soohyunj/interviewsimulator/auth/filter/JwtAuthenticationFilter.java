package soohyunj.interviewsimulator.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import soohyunj.interviewsimulator.auth.util.FilterExceptionProcessor;
import soohyunj.interviewsimulator.auth.util.JwtProvider;
import soohyunj.interviewsimulator.auth.dto.DecodedJwtToken;
import soohyunj.interviewsimulator.exception.BaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static soohyunj.interviewsimulator.redis.REDIS_HEADER.ACCESS_TOKEN;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final FilterExceptionProcessor filterExceptionProcessor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            if (token != null) {
                Authentication authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication); //SecurityContextHolder에 담기
            }
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            filterExceptionProcessor.excute(response, e);
        }
    }

    private Authentication getAuthentication(String token) {
        DecodedJwtToken decodedJwtToken = jwtProvider.decodeToken(token, ACCESS_TOKEN);

        String memberId = decodedJwtToken.memberId();
        String role = decodedJwtToken.role();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new UsernamePasswordAuthenticationToken(memberId, null, authorities);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else if (bearerToken != null) {
            return bearerToken;
        }
        return null;
    }
}
