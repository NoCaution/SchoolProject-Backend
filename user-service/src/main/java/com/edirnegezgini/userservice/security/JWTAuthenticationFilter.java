package com.edirnegezgini.userservice.security;

import com.edirnegezgini.commonservice.util.JWTUtil;
import com.edirnegezgini.userservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token;
        String authHeader = request.getHeader("CustomAuthorization");
        String id;
        boolean isGetAuthenticatedUserRequest;

        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        List<String> headerValues = Arrays.stream(authHeader.split(" ")).toList();
        token = headerValues.get(0);
        String headerId = "";
        String headerPassword = "";
        String headerRole = "";

        //if request is to get authenticated user, headerValue will be only token.
        //Otherwise, it will contain user's information
        isGetAuthenticatedUserRequest = headerValues.size() == 1;

        if(!isGetAuthenticatedUserRequest){
            headerId = headerValues.get(1);
            headerPassword = headerValues.get(2);
            headerRole = headerValues.get(3);
        }

        id = jwtUtil.extractUserId(token);

        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user;

            if(isGetAuthenticatedUserRequest){
                user = userService.loadUserByUsername(id);
            } else {
                user = new User(
                        headerId,
                        headerPassword,
                        Set.of(new SimpleGrantedAuthority(headerRole))
                );
            }

            if (jwtUtil.isTokenValid(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
