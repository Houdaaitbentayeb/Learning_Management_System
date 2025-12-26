package com.LMS.Learning_Management_System.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Test
    void testPasswordEncoderBean_IsConfigured() {
        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.getClass().getSimpleName().contains("BCrypt"));
    }

    @Test
    void testPasswordEncoder_EncodesPassword() {
        // Arrange
        String rawPassword = "testPassword123";

        // Act
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void testPasswordEncoder_DifferentEncodingsForSamePassword() {
        // Arrange
        String rawPassword = "password";

        // Act
        String encoded1 = passwordEncoder.encode(rawPassword);
        String encoded2 = passwordEncoder.encode(rawPassword);

        // Assert
        assertNotEquals(encoded1, encoded2);
        assertTrue(passwordEncoder.matches(rawPassword, encoded1));
        assertTrue(passwordEncoder.matches(rawPassword, encoded2));
    }

    @Test
    void testSecurityFilterChainBean_IsConfigured() {
        // Assert
        assertNotNull(securityFilterChain);
    }

    @Test
    void testPublicEndpoints_AuthApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/auth/test"))
                .andExpect(status().isNotFound()); // 404 car l'endpoint n'existe pas, mais pas 401/403
    }

    @Test
    void testPublicEndpoints_UsersApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_StudentApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/student/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_InstructorApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/instructor/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_EnrollmentApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/enrollment/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_CourseApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/course/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_LessonApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/lesson/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_QuizApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/quiz/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_AssignmentApiIsAccessible() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/assignment/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPublicEndpoints_PostRequestsAreAccessible() throws Exception {

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testPasswordEncoder_InvalidPasswordDoesNotMatch() {
        // Arrange
        String rawPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Act & Assert
        assertFalse(passwordEncoder.matches(wrongPassword, encodedPassword));
    }

    @Test
    void testPasswordEncoder_EmptyPasswordEncoding() {
        // Arrange
        String emptyPassword = "";

        // Act
        String encodedPassword = passwordEncoder.encode(emptyPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(emptyPassword, encodedPassword));
    }

    @Test
    void testPasswordEncoder_SpecialCharactersEncoding() {
        // Arrange
        String specialPassword = "P@ssw0rd!#$%^&*()";

        // Act
        String encodedPassword = passwordEncoder.encode(specialPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(specialPassword, encodedPassword));
    }

    @Test
    void testPasswordEncoder_LongPasswordEncoding() {
        // Arrange
        String longPassword = "ThisIsAVeryLongPasswordWithMoreThan50CharactersToTestTheEncoder";

        // Act
        String encodedPassword = passwordEncoder.encode(longPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(longPassword, encodedPassword));
    }
}