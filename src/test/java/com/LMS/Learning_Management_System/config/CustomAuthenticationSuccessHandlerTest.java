package com.LMS.Learning_Management_System.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationSuccessHandlerTest {

    @InjectMocks
    private CustomAuthenticationSuccessHandler successHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testOnAuthenticationSuccess_WithValidUser() throws IOException, ServletException {
        // Arrange
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_STUDENT"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
        UserDetails userDetails = User.builder()
                .username("test@example.com")
                .password("password")
                .authorities(authorities)
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
        writer.flush();

        String jsonResponse = stringWriter.toString();
        assertNotNull(jsonResponse);
        assertTrue(jsonResponse.contains("\"message\":\"Login successful\""));
        assertTrue(jsonResponse.contains("\"username\":\"test@example.com\""));
        assertTrue(jsonResponse.contains("\"roles\""));
    }

    @Test
    void testOnAuthenticationSuccess_WithInstructorRole() throws IOException, ServletException {
        // Arrange
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_INSTRUCTOR")
        );
        UserDetails userDetails = User.builder()
                .username("instructor@example.com")
                .password("password")
                .authorities(authorities)
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
        writer.flush();

        String jsonResponse = stringWriter.toString();
        assertTrue(jsonResponse.contains("\"username\":\"instructor@example.com\""));
        assertTrue(jsonResponse.contains("ROLE_INSTRUCTOR"));
    }

    @Test
    void testOnAuthenticationSuccess_WithMultipleRoles() throws IOException, ServletException {

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_INSTRUCTOR"),
                new SimpleGrantedAuthority("ROLE_STUDENT")
        );

        UserDetails userDetails = User.builder()
                .username("admin@example.com")
                .password("password")
                .authorities(authorities)
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");

        writer.flush();
        String jsonResponse = stringWriter.toString();
        assertTrue(jsonResponse.contains("admin@example.com"));
        assertTrue(jsonResponse.contains("ROLE_ADMIN"));
        assertTrue(jsonResponse.contains("ROLE_INSTRUCTOR"));
        assertTrue(jsonResponse.contains("ROLE_STUDENT"));
    }
    @Test
    void testOnAuthenticationSuccess_ResponseBodyContainsAllRequiredFields() throws IOException, ServletException {
        // Arrange
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_STUDENT")
        );
        UserDetails userDetails = User.builder()
                .username("student@example.com")
                .password("password")
                .authorities(authorities)
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        writer.flush();
        String jsonResponse = stringWriter.toString();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = mapper.readValue(jsonResponse, Map.class);

        assertEquals(3, responseMap.size());
        assertTrue(responseMap.containsKey("message"));
        assertTrue(responseMap.containsKey("username"));
        assertTrue(responseMap.containsKey("roles"));
        assertEquals("Login successful", responseMap.get("message"));
        assertEquals("student@example.com", responseMap.get("username"));
    }

    @Test
    void testOnAuthenticationSuccess_VerifyHttpResponseSettings() throws IOException, ServletException {
        // Arrange
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );
        UserDetails userDetails = User.builder()
                .username("user@example.com")
                .password("password")
                .authorities(authorities)
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).getWriter();
    }


}