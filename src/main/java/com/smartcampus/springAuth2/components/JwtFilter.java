package com.smartcampus.springAuth2.components;

import com.smartcampus.springAuth2.dtos.JwtUserDto;
import com.smartcampus.springAuth2.dtos.UserDetailDto;
import com.smartcampus.springAuth2.exceptions.ForbiddenException;
import com.smartcampus.springAuth2.exceptions.UnauthorizedException;
import com.smartcampus.springAuth2.services.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.smartcampus.springAuth2.utils.Constants.TOKEN_TYPE_ACCESS;
import static com.smartcampus.springAuth2.utils.Constants.TOKEN_TYPE_REFRESH;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {

            String urlRefresh = "/v1/auth/refresh";
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = "";
            // Obtener el access token
            if (authHeader.startsWith("Bearer")) {
                jwt =  authHeader.split(" ")[1].trim();
                validateToken(jwt,TOKEN_TYPE_ACCESS);
                this.SetAuthenticate(jwt,request);
                filterChain.doFilter(request, response);
                return;
            }
            // Obtener el refresh token
            if (authHeader.startsWith("Refresh") && request.getRequestURI().equals(urlRefresh) && request.getMethod().equals("GET")) {
                jwt = authHeader.split(" ")[1].trim();
                validateToken(jwt,TOKEN_TYPE_REFRESH);
                this.SetAuthenticate(jwt, request);
                filterChain.doFilter(request, response);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (ForbiddenException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void validateToken(String jwt, String tokenType) {
        if (!jwtUtil.isValid(jwt)) {
            if (tokenType.equals(TOKEN_TYPE_REFRESH)) {
                throw new ForbiddenException("El token expiro");
            }
            if (tokenType.equals(TOKEN_TYPE_ACCESS)) {
                throw new UnauthorizedException("El token no es válido");
            }
        }
        JwtUserDto userDto = jwtUtil.getUser(jwt);
        if (!userDto.getTokenType().equals(tokenType)) {
            throw new ForbiddenException("El token no es válido");
        }
    }

    private void SetAuthenticate(String jwt, HttpServletRequest request) {
        UserDetailDto userDetailDto = (UserDetailDto) this.userDetailService.loadUserByUsername(jwt);
//        BlacklistTokensEntity entity = this.tokenService.getBlacklistToken(userDetailDto.getSessionId());
//        if (entity != null) {
//            throw new UnauthorizedException("No tienes permiso");
//        }
//        if (userDetailDto.getAuthorities() != null) {
//            System.out.println(userDetailDto.getAuthorities().size());
//        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailDto, null, userDetailDto.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
