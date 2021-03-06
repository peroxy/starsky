package com.starsky.backend.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsky.backend.api.authentication.LoginRequest;
import com.starsky.backend.api.authentication.TokenResponse;
import com.starsky.backend.config.JwtConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, ObjectMapper mapper) {
        this.mapper = mapper;
        super.setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            var loginRequest = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            logger.warn(e.getMessage());
            throw new BadCredentialsException("Login failed", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        var expiresOn = Instant.now().plus(jwtConfig.getExpirationTime());
        var expiresIn = jwtConfig.getExpirationTime().getSeconds();
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withClaim("role", auth.getAuthorities().stream().findFirst().get().getAuthority())
                .withExpiresAt(Date.from(expiresOn))
                .sign(HMAC512(jwtConfig.getSecret().getBytes()));
        res.setContentType("application/json");
        res.getWriter().write(mapper.writeValueAsString(new TokenResponse(token, jwtConfig.getTokenPrefix().trim(), expiresOn, expiresIn)));
        res.getWriter().flush();
    }
}