package com.barogo.barogouserapi.config.security.filter;

import com.barogo.barogouserapi.config.exception.code.SecurityErrorCode;
import com.barogo.barogouserapi.config.jwt.JwtService;
import com.barogo.barogouserapi.domain.user.repository.UserAccessTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserAccessTokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var requestWrapper = new HttpServletRequestWrapper(request);

        String authHeader = requestWrapper.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(requestWrapper, response);
            return;
        }

        try {
            String jwt = authHeader.substring(7);
            String loginId = jwtService.extractUsername(jwt);
            if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginId);

                var isTokenValid = tokenRepository.findByToken(jwt)
                                                  .map(t -> !t.isExpired() && !t.isRevoked())
                                                  .orElse(false);
                if(!isTokenValid || !jwtService.isTokenValid(jwt, userDetails)) {
                    throw new UnavailableException("Unavailable Token");
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(requestWrapper)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
            filterChain.doFilter(requestWrapper, response);

        } catch (UnavailableException ex) {
            returnErrorResult(response, SecurityErrorCode.UNAVAILABLE_TOKEN);
        } catch (ExpiredJwtException ex) {
            returnErrorResult(response, SecurityErrorCode.EXPIRED_JWT_TOKEN);
        } catch (Exception ex) {
            returnErrorResult(response, SecurityErrorCode.INVALID_ACCESS);
        }

    }

    private void returnErrorResult (HttpServletResponse response, SecurityErrorCode err) throws IOException {
        var problemDetail = ProblemDetail.forStatusAndDetail(err.getStatus(), err.getMessage());
        problemDetail.setTitle(err.getCode());

        response.setStatus(err.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(problemDetail));
    }
}
