package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.dto.StudentDto;
import com.LMS.Learning_Management_System.entity.Enrollment;
import com.LMS.Learning_Management_System.service.EnrollmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EnrollmentController Tests")
class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private Enrollment mockEnrollment;
    private List<StudentDto> mockStudents;

    @BeforeEach
    void setUp() {
        // Configuration du mock Enrollment
        mockEnrollment = new Enrollment();
        mockEnrollment.setEnrollmentId(1);
        mockEnrollment.setEnrollmentDate(new Date());

        // Configuration des StudentDto mock
        StudentDto student1 = new StudentDto(1, "Ahmed", "Hassan");
        StudentDto student2 = new StudentDto(2, "Mohamed", "Ali");

        mockStudents = Arrays.asList(student1, student2);
    }

    @Test
    @DisplayName("Test enrollInCourse - Inscription réussie")
    void testEnrollInCourseSuccess() {
        // Arrange
        doNothing().when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = enrollmentController.enrollInCourse(mockEnrollment, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student enrolled successfully!", response.getBody());
        verify(enrollmentService, times(1)).enrollInCourse(mockEnrollment, request);
    }

    @Test
    @DisplayName("Test enrollInCourse - Échec avec IllegalArgumentException")
    void testEnrollInCourseFailure() {
        // Arrange
        doThrow(new IllegalArgumentException("Student already enrolled in this course"))
                .when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = enrollmentController.enrollInCourse(mockEnrollment, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Student already enrolled in this course", response.getBody());
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - Récupération réussie")
    void testViewEnrolledStudentsSuccess() {
        // Arrange
        int courseId = 1;
        when(enrollmentService.viewEnrolledStudents(courseId, request)).thenReturn(mockStudents);

        // Act
        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(courseId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        @SuppressWarnings("unchecked")
        List<StudentDto> students = (List<StudentDto>) response.getBody();
        assertEquals(2, students.size());
        verify(enrollmentService, times(1)).viewEnrolledStudents(courseId, request);
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - Aucun étudiant inscrit")
    void testViewEnrolledStudentsEmpty() {
        // Arrange
        int courseId = 1;
        when(enrollmentService.viewEnrolledStudents(courseId, request)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(courseId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        @SuppressWarnings("unchecked")
        List<StudentDto> students = (List<StudentDto>) response.getBody();
        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - Échec avec IllegalArgumentException")
    void testViewEnrolledStudentsFailure() {
        // Arrange
        int courseId = 999;
        when(enrollmentService.viewEnrolledStudents(courseId, request))
                .thenThrow(new IllegalArgumentException("Course not found"));

        // Act
        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(courseId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Course not found", response.getBody());
    }

    @Test
    @DisplayName("Test removeEnrolledStudent - Suppression réussie")
    void testRemoveEnrolledStudentSuccess() {
        // Arrange
        int studentId = 1;
        int courseId = 1;
        doNothing().when(enrollmentService).removeEnrolledStudent(courseId, studentId, request);

        // Act
        ResponseEntity<String> response = enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student removed successfully.", response.getBody());
        verify(enrollmentService, times(1)).removeEnrolledStudent(courseId, studentId, request);
    }

    @Test
    @DisplayName("Test removeEnrolledStudent - Échec avec IllegalArgumentException")
    void testRemoveEnrolledStudentFailure() {
        // Arrange
        int studentId = 1;
        int courseId = 1;
        doThrow(new IllegalArgumentException("Enrollment not found"))
                .when(enrollmentService).removeEnrolledStudent(courseId, studentId, request);

        // Act
        ResponseEntity<String> response = enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Enrollment not found", response.getBody());
    }

    @Test
    @DisplayName("Test enrollInCourse - Enrollment null")
    void testEnrollInCourseNullEnrollment() {
        // Arrange
        doThrow(new IllegalArgumentException("Enrollment cannot be null"))
                .when(enrollmentService).enrollInCourse(isNull(), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = enrollmentController.enrollInCourse(null, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Enrollment cannot be null", response.getBody());
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - CourseId invalide")
    void testViewEnrolledStudentsInvalidCourseId() {
        // Arrange
        int invalidCourseId = -1;
        when(enrollmentService.viewEnrolledStudents(invalidCourseId, request))
                .thenThrow(new IllegalArgumentException("Invalid course ID"));

        // Act
        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(invalidCourseId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid course ID", response.getBody());
    }

    @Test
    @DisplayName("Test removeEnrolledStudent - StudentId invalide")
    void testRemoveEnrolledStudentInvalidStudentId() {
        // Arrange
        int invalidStudentId = -1;
        int courseId = 1;
        doThrow(new IllegalArgumentException("Invalid student ID"))
                .when(enrollmentService).removeEnrolledStudent(courseId, invalidStudentId, request);

        // Act
        ResponseEntity<String> response = enrollmentController.removeEnrolledStudent(invalidStudentId, courseId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid student ID", response.getBody());
    }

    @Test
    @DisplayName("Test enrollInCourse - Vérification des appels au service")
    void testEnrollInCourseServiceCall() {
        // Arrange
        doNothing().when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        // Act
        enrollmentController.enrollInCourse(mockEnrollment, request);

        // Assert
        verify(enrollmentService, times(1)).enrollInCourse(mockEnrollment, request);
        verify(enrollmentService, only()).enrollInCourse(mockEnrollment, request);
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - Vérification du nombre d'étudiants")
    void testViewEnrolledStudentsCount() {
        // Arrange
        int courseId = 1;
        when(enrollmentService.viewEnrolledStudents(courseId, request)).thenReturn(mockStudents);

        // Act
        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(courseId, request);

        // Assert
        @SuppressWarnings("unchecked")
        List<StudentDto> students = (List<StudentDto>) response.getBody();
        assertEquals(2, students.size());
        assertEquals("Ahmed", students.get(0).getFirstName());
        assertEquals("Mohamed", students.get(1).getFirstName());
    }

    @Test
    @DisplayName("Test removeEnrolledStudent - Vérification des paramètres")
    void testRemoveEnrolledStudentParameters() {
        // Arrange
        int studentId = 5;
        int courseId = 10;
        doNothing().when(enrollmentService).removeEnrolledStudent(courseId, studentId, request);

        // Act
        enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        // Assert
        verify(enrollmentService, times(1))
                .removeEnrolledStudent(courseId, studentId, request);
    }

    @Test
    @DisplayName("Test enrollInCourse - Étudiant déjà inscrit")
    void testEnrollInCourseAlreadyEnrolled() {
        // Arrange
        doThrow(new IllegalArgumentException("Student is already enrolled in this course"))
                .when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = enrollmentController.enrollInCourse(mockEnrollment, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("already enrolled"));
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - CourseId zéro")
    void testViewEnrolledStudentsZeroCourseId() {
        // Arrange
        int courseId = 0;
        when(enrollmentService.viewEnrolledStudents(courseId, request))
                .thenThrow(new IllegalArgumentException("Course ID must be greater than 0"));

        // Act
        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(courseId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("must be greater than 0"));
    }

    @Test
    @DisplayName("Test removeEnrolledStudent - Étudiant non inscrit")
    void testRemoveEnrolledStudentNotEnrolled() {
        // Arrange
        int studentId = 1;
        int courseId = 1;
        doThrow(new IllegalArgumentException("Student is not enrolled in this course"))
                .when(enrollmentService).removeEnrolledStudent(courseId, studentId, request);

        // Act
        ResponseEntity<String> response = enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("not enrolled"));
    }

    @Test
    @DisplayName("Test enrollInCourse puis viewEnrolledStudents - Scénario complet")
    void testEnrollThenViewScenario() {
        // Arrange - Enroll
        int courseId = 1;
        doNothing().when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        // Act - Enroll
        ResponseEntity<String> enrollResponse = enrollmentController.enrollInCourse(mockEnrollment, request);

        // Assert - Enroll
        assertEquals(HttpStatus.OK, enrollResponse.getStatusCode());

        // Arrange - View
        when(enrollmentService.viewEnrolledStudents(courseId, request)).thenReturn(mockStudents);

        // Act - View
        ResponseEntity<?> viewResponse = enrollmentController.viewEnrolledStudents(courseId, request);

        // Assert - View
        assertEquals(HttpStatus.OK, viewResponse.getStatusCode());
        @SuppressWarnings("unchecked")
        List<StudentDto> students = (List<StudentDto>) viewResponse.getBody();
        assertFalse(students.isEmpty());
    }

    @Test
    @DisplayName("Test enrollInCourse puis removeEnrolledStudent - Scénario complet")
    void testEnrollThenRemoveScenario() {
        // Arrange - Enroll
        doNothing().when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        // Act - Enroll
        ResponseEntity<String> enrollResponse = enrollmentController.enrollInCourse(mockEnrollment, request);

        // Assert - Enroll
        assertEquals(HttpStatus.OK, enrollResponse.getStatusCode());

        // Arrange - Remove
        int studentId = 1;
        int courseId = 1;
        doNothing().when(enrollmentService).removeEnrolledStudent(courseId, studentId, request);

        // Act - Remove
        ResponseEntity<String> removeResponse = enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        // Assert - Remove
        assertEquals(HttpStatus.OK, removeResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - Plusieurs cours")
    void testViewEnrolledStudentsMultipleCourses() {
        // Arrange
        int courseId1 = 1;
        int courseId2 = 2;

        List<StudentDto> students1 = Arrays.asList(mockStudents.get(0));
        List<StudentDto> students2 = Arrays.asList(mockStudents.get(1));

        when(enrollmentService.viewEnrolledStudents(courseId1, request)).thenReturn(students1);
        when(enrollmentService.viewEnrolledStudents(courseId2, request)).thenReturn(students2);

        // Act
        ResponseEntity<?> response1 = enrollmentController.viewEnrolledStudents(courseId1, request);
        ResponseEntity<?> response2 = enrollmentController.viewEnrolledStudents(courseId2, request);

        // Assert
        @SuppressWarnings("unchecked")
        List<StudentDto> result1 = (List<StudentDto>) response1.getBody();
        @SuppressWarnings("unchecked")
        List<StudentDto> result2 = (List<StudentDto>) response2.getBody();

        assertEquals(1, result1.size());
        assertEquals(1, result2.size());
        assertNotEquals(result1.get(0).getUserAccountId(), result2.get(0).getUserAccountId());
    }

    @Test
    @DisplayName("Test removeEnrolledStudent - Cours n'existe pas")
    void testRemoveEnrolledStudentCourseNotFound() {
        // Arrange
        int studentId = 1;
        int courseId = 999;
        doThrow(new IllegalArgumentException("Course with ID 999 not found"))
                .when(enrollmentService).removeEnrolledStudent(courseId, studentId, request);

        // Act
        ResponseEntity<String> response = enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("not found"));
    }

    @Test
    @DisplayName("Test enrollInCourse - Plusieurs inscriptions successives")
    void testEnrollInCourseMultipleEnrollments() {
        // Arrange
        doNothing().when(enrollmentService).enrollInCourse(any(Enrollment.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response1 = enrollmentController.enrollInCourse(mockEnrollment, request);
        ResponseEntity<String> response2 = enrollmentController.enrollInCourse(mockEnrollment, request);

        // Assert
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        verify(enrollmentService, times(2)).enrollInCourse(mockEnrollment, request);
    }

    @Test
    @DisplayName("Test viewEnrolledStudents - Vérification de l'ordre des étudiants")
    void testViewEnrolledStudentsOrder() {
        // Arrange
        int courseId = 1;
        when(enrollmentService.viewEnrolledStudents(courseId, request)).thenReturn(mockStudents);

        // Act
        ResponseEntity<?> response = enrollmentController.viewEnrolledStudents(courseId, request);

        // Assert
        @SuppressWarnings("unchecked")
        List<StudentDto> students = (List<StudentDto>) response.getBody();
        assertEquals("Ahmed", students.get(0).getFirstName());
        assertEquals("Mohamed", students.get(1).getFirstName());
    }

    @Test
    @DisplayName("Test removeEnrolledStudent - StudentId et CourseId valides")
    void testRemoveEnrolledStudentValidIds() {
        // Arrange
        int studentId = 1;
        int courseId = 1;
        doNothing().when(enrollmentService).removeEnrolledStudent(courseId, studentId, request);

        // Act
        ResponseEntity<String> response = enrollmentController.removeEnrolledStudent(studentId, courseId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("removed successfully"));
    }
}