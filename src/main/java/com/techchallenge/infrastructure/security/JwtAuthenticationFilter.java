package com.techchallenge.infrastructure.security;

import com.techchallenge.domain.auth.service.TokenExtractor;
import com.techchallenge.domain.auth.service.TokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenExtractor tokenExtractor;
    private final TokenValidator tokenValidator;

    public JwtAuthenticationFilter(TokenExtractor tokenExtractor, TokenValidator tokenValidator) {
        this.tokenExtractor = tokenExtractor;
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(httpRequest);
        if (token != null && tokenValidator.isTokenValid(token)) {
            String username = tokenExtractor.extractUsername(token);
            String tokenType = tokenExtractor.extractTokenType(token);

            // Criar authorities baseado no tipo de token
            List<SimpleGrantedAuthority> authorities = createAuthorities(tokenType);

            // Criar authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

            // Definir no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(httpRequest, httpResponse);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private List<SimpleGrantedAuthority> createAuthorities(String tokenType) {
        return switch (tokenType) {
            case "USER" -> List.of(new SimpleGrantedAuthority("ROLE_USER"));
            case "CLIENT" -> List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
            default -> List.of();
        };
    }

}
