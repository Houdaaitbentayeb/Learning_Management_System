package com.LMS.Learning_Management_System.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserSignUpRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test création d'un UserSignUpRequest valide")
    void testValidUserSignUpRequest() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty(), "Il ne devrait pas y avoir de violations pour un objet valide");
    }

    @Test
    @DisplayName("Test getters et setters")
    void testGettersAndSetters() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        String email = "user@test.com";
        String password = "securePassword";
        Integer userTypeId = 2;

        // Act
        request.setEmail(email);
        request.setPassword(password);
        request.setUserTypeId(userTypeId);

        // Assert
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
        assertEquals(userTypeId, request.getUserTypeId());
    }

    @Test
    @DisplayName("Test validation avec email vide")
    void testEmptyEmail() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("");
        request.setPassword("password123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Test validation avec email null")
    void testNullEmail() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail(null);
        request.setPassword("password123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Test validation avec format email invalide")
    void testInvalidEmailFormat() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("invalid-email");
        request.setPassword("password123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Test validation avec email sans domaine")
    void testEmailWithoutDomain() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("user@");
        request.setPassword("password123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Test validation avec email sans @")
    void testEmailWithoutAtSign() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("userexample.com");
        request.setPassword("password123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Test validation avec password vide")
    void testEmptyPassword() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    @DisplayName("Test validation avec password null")
    void testNullPassword() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword(null);
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    @DisplayName("Test validation avec userTypeId null")
    void testNullUserTypeId() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUserTypeId(null);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("userTypeId")));
    }

    @Test
    @DisplayName("Test validation avec userTypeId = 0")
    void testUserTypeIdZero() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUserTypeId(0);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty(), "userTypeId=0 devrait être invalide avec @Positive");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("userTypeId")));
    }

    @Test
    @DisplayName("Test validation avec userTypeId négatif")
    void testNegativeUserTypeId() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUserTypeId(-1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty(), "userTypeId négatif devrait être invalide avec @Positive");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("userTypeId")));
    }

    @Test
    @DisplayName("Test validation avec tous les champs invalides")
    void testAllFieldsInvalid() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("");
        request.setPassword("");
        request.setUserTypeId(null);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());
    }

    @Test
    @DisplayName("Test email avec caractères spéciaux valides")
    void testEmailWithValidSpecialCharacters() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("user.name+tag@example.co.uk");
        request.setPassword("password123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Test password avec espaces")
    void testPasswordWithSpaces() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("pass word 123");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty(), "Le password avec espaces devrait être valide");
    }

    @Test
    @DisplayName("Test password avec caractères spéciaux")
    void testPasswordWithSpecialCharacters() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("P@ssw0rd!#$%");
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Test password très long")
    void testVeryLongPassword() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("a".repeat(1000));
        request.setUserTypeId(1);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty(), "Un long password devrait être valide");
    }

    @Test
    @DisplayName("Test userTypeId avec valeur maximale")
    void testMaxUserTypeId() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUserTypeId(Integer.MAX_VALUE);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
        assertEquals(Integer.MAX_VALUE, request.getUserTypeId());
    }

    @Test
    @DisplayName("Test création d'objet par défaut")
    void testDefaultConstructor() {
        // Act
        UserSignUpRequest request = new UserSignUpRequest();

        // Assert
        assertNotNull(request);
        assertNull(request.getEmail());
        assertNull(request.getPassword());
        assertNull(request.getUserTypeId());
    }

    @Test
    @DisplayName("Test modification successive des champs")
    void testMultipleSetterCalls() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();

        // Act
        request.setEmail("first@example.com");
        request.setEmail("second@example.com");
        request.setPassword("password1");
        request.setPassword("password2");
        request.setUserTypeId(1);
        request.setUserTypeId(2);

        // Assert
        assertEquals("second@example.com", request.getEmail());
        assertEquals("password2", request.getPassword());
        assertEquals(2, request.getUserTypeId());
    }

    @Test
    @DisplayName("Test userTypeId avec valeur positive valide")
    void testValidPositiveUserTypeId() {
        // Arrange
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUserTypeId(5);

        // Act
        Set<ConstraintViolation<UserSignUpRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }
}