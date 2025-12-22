package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
    }

    @Test
    void testGetAndSetEmail() {
        // Arrange
        String expectedEmail = "user@example.com";

        // Act
        loginRequest.setEmail(expectedEmail);
        String actualEmail = loginRequest.getEmail();

        // Assert
        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    void testGetAndSetPassword() {
        // Arrange
        String expectedPassword = "securePassword123";

        // Act
        loginRequest.setPassword(expectedPassword);
        String actualPassword = loginRequest.getPassword();

        // Assert
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    void testEmailIsNullByDefault() {
        // Assert
        assertNull(loginRequest.getEmail());
    }

    @Test
    void testPasswordIsNullByDefault() {
        // Assert
        assertNull(loginRequest.getPassword());
    }

    @Test
    void testSetEmailWithNull() {
        // Act
        loginRequest.setEmail(null);

        // Assert
        assertNull(loginRequest.getEmail());
    }

    @Test
    void testSetPasswordWithNull() {
        // Act
        loginRequest.setPassword(null);

        // Assert
        assertNull(loginRequest.getPassword());
    }

    @Test
    void testSetEmailWithEmptyString() {
        // Arrange
        String emptyEmail = "";

        // Act
        loginRequest.setEmail(emptyEmail);

        // Assert
        assertEquals(emptyEmail, loginRequest.getEmail());
    }

    @Test
    void testSetPasswordWithEmptyString() {
        // Arrange
        String emptyPassword = "";

        // Act
        loginRequest.setPassword(emptyPassword);

        // Assert
        assertEquals(emptyPassword, loginRequest.getPassword());
    }

    @Test
    void testCompleteLoginRequest() {
        // Arrange
        String email = "admin@lms.com";
        String password = "AdminPass2024!";

        // Act
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        // Assert
        assertAll(
                () -> assertEquals(email, loginRequest.getEmail()),
                () -> assertEquals(password, loginRequest.getPassword())
        );
    }
}
