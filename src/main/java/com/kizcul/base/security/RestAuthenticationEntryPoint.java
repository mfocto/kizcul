package com.kizcul.base.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kizcul.base.exception.ApiErrorInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        if (InsufficientAuthenticationException.class == authException.getClass()) {
            apiErrorInfo.setMessage("Not Logined!!!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        else {
            apiErrorInfo.setMessage("Bas Request!!!");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        String jsonString = objectMapper.writeValueAsString(apiErrorInfo);
        response.getWriter().write(jsonString);
    }
}
