package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.dto.LoginRequest;
import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.service.UsersService;
import com.LMS.Learning_Management_System.util.UserSignUpRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    private UserSignUpRequest signUpRequest;
    private LoginRequest loginRequest;
    private Users mockUser;

    @BeforeEach
    void setUp() {
        // Configuration du SignUpRequest
        signUpRequest = new UserSignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        // Note: Ajuster selon la vraie structure de UserSignUpRequest
        // signUpRequest.setRole("STUDENT"); // Si la méthode existe
        // signUpRequest.setUserType("STUDENT"); // Ou
        // signUpRequest.setUserTypeId(1); // Ou autre

        // Configuration du LoginRequest
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        // Configuration du mock User
        mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");

        // Clear SecurityContext
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Test signUp - Inscription réussie")
    void testSignUpSuccess() {
        // Arrange
        doNothing().when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = authController.signUp(signUpRequest, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(usersService, times(1)).save(signUpRequest, request);
    }

    @Test
    @DisplayName("Test signUp - Échec avec IllegalArgumentException")
    void testSignUpFailureIllegalArgument() {
        // Arrange
        doThrow(new IllegalArgumentException("Email already exists"))
                .when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = authController.signUp(signUpRequest, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    @DisplayName("Test signUp - Échec avec EntityNotFoundException")
    void testSignUpFailureEntityNotFound() {
        // Arrange
        doThrow(new EntityNotFoundException("Role not found"))
                .when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = authController.signUp(signUpRequest, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Role not found", response.getBody());
    }

    @Test
    @DisplayName("Test login - Connexion réussie")
    void testLoginSuccess() {
        // Arrange
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(request.getSession()).thenReturn(session);

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Login successful"));
        assertTrue(response.getBody().contains(mockUser.getEmail()));
        verify(session, times(1)).setAttribute("USER_ID", mockUser.getUserId());
        verify(session, times(1)).setAttribute(eq("USER_TYPE_ID"), any());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Test login - Email invalide (utilisateur non trouvé)")
    void testLoginInvalidEmail() {
        // Arrange
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(null);

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody());
        verify(usersService, times(1)).findByEmail(loginRequest.getEmail());
        verify(usersService, never()).validatePassword(anyString(), anyString());
    }

    @Test
    @DisplayName("Test login - Mot de passe invalide")
    void testLoginInvalidPassword() {
        // Arrange
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(false);

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email or password.", response.getBody());
    }

    @Test
    @DisplayName("Test login - UsernameNotFoundException")
    void testLoginUsernameNotFoundException() {
        // Arrange
        when(usersService.findByEmail(loginRequest.getEmail()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email or password.", response.getBody());
    }

    @Test
    @DisplayName("Test logout - Déconnexion réussie")
    void testLogoutSuccess() {
        when(request.getSession()).thenReturn(session);

        ResponseEntity<String> response = authController.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully logged out", response.getBody());
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(session, times(1)).invalidate();
    }

    @Test
    @DisplayName("Test signUp - Email vide")
    void testSignUpWithEmptyEmail() {
        // Arrange
        signUpRequest.setEmail("");
        doThrow(new IllegalArgumentException("Email cannot be empty"))
                .when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = authController.signUp(signUpRequest, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email cannot be empty", response.getBody());
    }

    @Test
    @DisplayName("Test signUp - Mot de passe vide")
    void testSignUpWithEmptyPassword() {
        // Arrange
        signUpRequest.setPassword("");
        doThrow(new IllegalArgumentException("Password cannot be empty"))
                .when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = authController.signUp(signUpRequest, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Password cannot be empty", response.getBody());
    }

    @Test
    @DisplayName("Test login - Email vide")
    void testLoginWithEmptyEmail() {
        // Arrange
        loginRequest.setEmail("");
        when(usersService.findByEmail("")).thenReturn(null);

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody());
    }

    @Test
    @DisplayName("Test login - Mot de passe vide")
    void testLoginWithEmptyPassword() {
        // Arrange
        loginRequest.setPassword("");
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword("", mockUser.getPassword())).thenReturn(false);

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email or password.", response.getBody());
    }

    @Test
    @DisplayName("Test login - Session créée avec succès")
    void testLoginSessionCreated() {
        // Arrange
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(request.getSession()).thenReturn(session);

        // Act
        authController.login(request, loginRequest);

        // Assert
        verify(session, times(1)).setAttribute("USER_ID", mockUser.getUserId());
        verify(session, times(1)).setAttribute(eq("USER_TYPE_ID"), any());
    }

    @Test
    @DisplayName("Test login - SecurityContext configuré")
    void testLoginSecurityContextSet() {
        // Arrange
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(request.getSession()).thenReturn(session);

        // Act
        authController.login(request, loginRequest);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(loginRequest.getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    @DisplayName("Test logout - SecurityContext nettoyé")
    void testLogoutClearsSecurityContext() {
        when(request.getSession()).thenReturn(session);
        // Arrange - Set authentication first
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        "user@test.com", null, java.util.List.of()
                )
        );

        // Act
        authController.logout(request);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(session, times(1)).invalidate();

    }

    @Test
    @DisplayName("Test signUp - Rôle invalide")
    void testSignUpWithInvalidRole() {

        doThrow(new EntityNotFoundException("Role INVALID_ROLE not found"))
                .when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = authController.signUp(signUpRequest, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Role INVALID_ROLE not found", response.getBody());
    }

    @Test
    @DisplayName("Test login - Email avec format invalide")
    void testLoginWithInvalidEmailFormat() {
        // Arrange
        loginRequest.setEmail("invalid-email");
        when(usersService.findByEmail("invalid-email")).thenReturn(null);

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody());
    }

    @Test
    @DisplayName("Test login - Plusieurs tentatives échouées")
    void testLoginMultipleFailedAttempts() {
        // Arrange
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(false);

        // Act
        ResponseEntity<String> response1 = authController.login(request, loginRequest);
        ResponseEntity<String> response2 = authController.login(request, loginRequest);
        ResponseEntity<String> response3 = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
        verify(usersService, times(3)).validatePassword(loginRequest.getPassword(), mockUser.getPassword());
    }

    @Test
    @DisplayName("Test signUp puis login - Scénario complet")
    void testSignUpThenLoginFullScenario() {
        // Arrange - SignUp
        doNothing().when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act - SignUp
        ResponseEntity<String> signUpResponse = authController.signUp(signUpRequest, request);

        // Assert - SignUp
        assertEquals(HttpStatus.OK, signUpResponse.getStatusCode());

        // Arrange - Login
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(request.getSession()).thenReturn(session);

        // Act - Login
        ResponseEntity<String> loginResponse = authController.login(request, loginRequest);

        // Assert - Login
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertTrue(loginResponse.getBody().contains("Login successful"));
    }

    @Test
    @DisplayName("Test login puis logout - Scénario complet")
    void testLoginThenLogoutFullScenario() {
        // Arrange - Login
        when(usersService.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);
        when(usersService.validatePassword(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(request.getSession()).thenReturn(session);

        // Act - Login
        ResponseEntity<String> loginResponse = authController.login(request, loginRequest);

        // Assert - Login
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // Act - Logout
        ResponseEntity<String> logoutResponse = authController.logout(request);

        // Assert - Logout
        assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Test signUp - Email déjà existant")
    void testSignUpWithExistingEmail() {
        // Arrange
        doThrow(new IllegalArgumentException("User with email test@example.com already exists"))
                .when(usersService).save(any(UserSignUpRequest.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = authController.signUp(signUpRequest, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("already exists"));
    }

    @Test
    @DisplayName("Test login - User null après findByEmail")
    void testLoginUserNullAfterFind() {
        // Arrange
        when(usersService.findByEmail(anyString())).thenReturn(null);

        // Act
        ResponseEntity<String> response = authController.login(request, loginRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody());
        verify(usersService, never()).validatePassword(anyString(), anyString());
    }

    @Test
    @DisplayName("Test logout - Déjà déconnecté")
    void testLogoutWhenAlreadyLoggedOut() {
        when(request.getSession()).thenReturn(session);
        // Arrange - Already logged out
        SecurityContextHolder.clearContext();

        // Act
        ResponseEntity<String> response = authController.logout(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully logged out", response.getBody());
        verify(session, times(1)).invalidate();

    }
}