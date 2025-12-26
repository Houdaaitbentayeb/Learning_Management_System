package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionAuthServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private SessionAuthService sessionAuthService;

    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users();
        user.setUserId(1);
    }

    // ==================== SUCCESS ====================

    @Test
    void requireUser_success() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USER_ID")).thenReturn(1);
        when(usersRepository.findById(1)).thenReturn(Optional.of(user));

        Users result = sessionAuthService.requireUser(request);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    // ==================== NO USER IN SESSION ====================

    @Test
    void requireUser_noUserInSession() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USER_ID")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sessionAuthService.requireUser(request)
        );

        assertEquals("No user is logged in.", exception.getMessage());
        verify(usersRepository, never()).findById(anyInt());
    }

    // ==================== USER NOT FOUND IN DB ====================

    @Test
    void requireUser_userNotFound() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USER_ID")).thenReturn(1);
        when(usersRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sessionAuthService.requireUser(request)
        );

        assertEquals("User not found.", exception.getMessage());
    }
}
