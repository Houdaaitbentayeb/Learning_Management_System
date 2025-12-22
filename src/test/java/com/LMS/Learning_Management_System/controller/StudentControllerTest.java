package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.entity.Student;
import com.LMS.Learning_Management_System.service.NotificationsService;
import com.LMS.Learning_Management_System.service.StudentService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StudentController Tests")
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private NotificationsService notificationsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private StudentController studentController;

    private Student mockStudent;
    private List<String> mockNotifications;

    @BeforeEach
    void setUp() {
        // Configuration du mock Student
        mockStudent = new Student();
        mockStudent.setUserAccountId(1);
        mockStudent.setFirstName("Ahmed");
        mockStudent.setLastName("Hassan");

        // Configuration des notifications mock
        mockNotifications = Arrays.asList(
                "Notification 1: Nouveau cours disponible",
                "Notification 2: Devoir noté",
                "Notification 3: Quiz disponible"
        );
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour réussie")
    void testUpdateUserSuccess() {
        // Arrange
        int studentId = 1;
        doNothing().when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student updated successfully.", response.getBody());
        verify(studentService, times(1)).save(studentId, mockStudent, request);
    }

    @Test
    @DisplayName("Test updateUser - Échec avec IllegalArgumentException")
    void testUpdateUserFailure() {
        // Arrange
        int studentId = 1;
        doThrow(new IllegalArgumentException("Student not found"))
                .when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Student not found", response.getBody());
    }

    @Test
    @DisplayName("Test getAllNotifications - Récupération réussie")
    void testGetAllNotificationsSuccess() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        List<String> notifications = studentController.getAllNotifications(userId, request);

        // Assert
        assertNotNull(notifications);
        assertEquals(3, notifications.size());
        assertEquals("Notification 1: Nouveau cours disponible", notifications.get(0));
        verify(notificationsService, times(1)).getAllNotifications(userId, request);
    }

    @Test
    @DisplayName("Test getAllNotifications - Liste vide")
    void testGetAllNotificationsEmpty() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(Collections.emptyList());

        // Act
        List<String> notifications = studentController.getAllNotifications(userId, request);

        // Assert
        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
        verify(notificationsService, times(1)).getAllNotifications(userId, request);
    }

    @Test
    @DisplayName("Test getUnreadNotifications - Récupération réussie")
    void testGetUnreadNotificationsSuccess() {
        // Arrange
        int userId = 1;
        List<String> unreadNotifications = Arrays.asList(
                "Notification non lue 1",
                "Notification non lue 2"
        );
        when(notificationsService.getAllUnreadNotifications(userId, request)).thenReturn(unreadNotifications);

        // Act
        List<String> notifications = studentController.getUnreadNotifications(userId, request);

        // Assert
        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        assertEquals("Notification non lue 1", notifications.get(0));
        verify(notificationsService, times(1)).getAllUnreadNotifications(userId, request);
    }

    @Test
    @DisplayName("Test getUnreadNotifications - Aucune notification non lue")
    void testGetUnreadNotificationsEmpty() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllUnreadNotifications(userId, request)).thenReturn(Collections.emptyList());

        // Act
        List<String> notifications = studentController.getUnreadNotifications(userId, request);

        // Assert
        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
        verify(notificationsService, times(1)).getAllUnreadNotifications(userId, request);
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour du prénom")
    void testUpdateUserFirstName() {
        // Arrange
        int studentId = 1;
        mockStudent.setFirstName("Mohamed");
        doNothing().when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mohamed", mockStudent.getFirstName());
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour du nom")
    void testUpdateUserLastName() {
        // Arrange
        int studentId = 1;
        mockStudent.setLastName("Ali");
        doNothing().when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ali", mockStudent.getLastName());
    }

    @Test
    @DisplayName("Test updateUser - StudentId invalide")
    void testUpdateUserInvalidId() {
        // Arrange
        int invalidStudentId = -1;
        doThrow(new IllegalArgumentException("Invalid student ID"))
                .when(studentService).save(eq(invalidStudentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(invalidStudentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid student ID", response.getBody());
    }

    @Test
    @DisplayName("Test updateUser - Student null")
    void testUpdateUserNullStudent() {
        // Arrange
        int studentId = 1;
        doThrow(new IllegalArgumentException("Student cannot be null"))
                .when(studentService).save(eq(studentId), isNull(), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, null, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Student cannot be null", response.getBody());
    }

    @Test
    @DisplayName("Test getAllNotifications - UserId invalide")
    void testGetAllNotificationsInvalidUserId() {
        // Arrange
        int invalidUserId = -1;
        when(notificationsService.getAllNotifications(invalidUserId, request))
                .thenReturn(Collections.emptyList());

        // Act
        List<String> notifications = studentController.getAllNotifications(invalidUserId, request);

        // Assert
        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
    }

    @Test
    @DisplayName("Test getUnreadNotifications - UserId invalide")
    void testGetUnreadNotificationsInvalidUserId() {
        // Arrange
        int invalidUserId = 0;
        when(notificationsService.getAllUnreadNotifications(invalidUserId, request))
                .thenReturn(Collections.emptyList());

        // Act
        List<String> notifications = studentController.getUnreadNotifications(invalidUserId, request);

        // Assert
        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
    }

    @Test
    @DisplayName("Test getAllNotifications - Vérification de l'ordre des notifications")
    void testGetAllNotificationsOrder() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        List<String> notifications = studentController.getAllNotifications(userId, request);

        // Assert
        assertEquals("Notification 1: Nouveau cours disponible", notifications.get(0));
        assertEquals("Notification 2: Devoir noté", notifications.get(1));
        assertEquals("Notification 3: Quiz disponible", notifications.get(2));
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour multiple des champs")
    void testUpdateUserMultipleFields() {
        // Arrange
        int studentId = 1;
        mockStudent.setFirstName("NewFirstName");
        mockStudent.setLastName("NewLastName");
        doNothing().when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("NewFirstName", mockStudent.getFirstName());
        assertEquals("NewLastName", mockStudent.getLastName());
    }

    @Test
    @DisplayName("Test getAllNotifications puis getUnreadNotifications - Comparaison")
    void testCompareAllAndUnreadNotifications() {
        // Arrange
        int userId = 1;
        List<String> unreadNotifications = Arrays.asList(
                "Notification 1: Nouveau cours disponible"
        );
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);
        when(notificationsService.getAllUnreadNotifications(userId, request)).thenReturn(unreadNotifications);

        // Act
        List<String> allNotifications = studentController.getAllNotifications(userId, request);
        List<String> unread = studentController.getUnreadNotifications(userId, request);

        // Assert
        assertEquals(3, allNotifications.size());
        assertEquals(1, unread.size());
        assertTrue(allNotifications.size() > unread.size());
    }

    @Test
    @DisplayName("Test updateUser - Student existant non trouvé")
    void testUpdateUserNotFound() {
        // Arrange
        int studentId = 999;
        doThrow(new IllegalArgumentException("Student with ID 999 not found"))
                .when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("not found"));
    }

    @Test
    @DisplayName("Test getAllNotifications - Service appelé avec les bons paramètres")
    void testGetAllNotificationsServiceCall() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        studentController.getAllNotifications(userId, request);

        // Assert
        verify(notificationsService, times(1)).getAllNotifications(userId, request);
    }

    @Test
    @DisplayName("Test getUnreadNotifications - Service appelé avec les bons paramètres")
    void testGetUnreadNotificationsServiceCall() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllUnreadNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        studentController.getUnreadNotifications(userId, request);

        // Assert
        verify(notificationsService, times(1)).getAllUnreadNotifications(userId, request);
    }

    @Test
    @DisplayName("Test updateUser - Vérification des appels au service")
    void testUpdateUserServiceCallVerification() {
        // Arrange
        int studentId = 1;
        doNothing().when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        studentController.updateUser(studentId, mockStudent, request);

        // Assert
        verify(studentService, times(1)).save(studentId, mockStudent, request);
        verify(studentService, only()).save(studentId, mockStudent, request);
    }

    @Test
    @DisplayName("Test getAllNotifications - Plusieurs appels consécutifs")
    void testGetAllNotificationsMultipleCalls() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        List<String> notifications1 = studentController.getAllNotifications(userId, request);
        List<String> notifications2 = studentController.getAllNotifications(userId, request);

        // Assert
        assertEquals(notifications1.size(), notifications2.size());
        verify(notificationsService, times(2)).getAllNotifications(userId, request);
    }

    @Test
    @DisplayName("Test getUnreadNotifications - Plusieurs utilisateurs")
    void testGetUnreadNotificationsMultipleUsers() {
        // Arrange
        int userId1 = 1;
        int userId2 = 2;
        List<String> notifications1 = Arrays.asList("Notification user 1");
        List<String> notifications2 = Arrays.asList("Notification user 2");

        when(notificationsService.getAllUnreadNotifications(userId1, request)).thenReturn(notifications1);
        when(notificationsService.getAllUnreadNotifications(userId2, request)).thenReturn(notifications2);

        // Act
        List<String> result1 = studentController.getUnreadNotifications(userId1, request);
        List<String> result2 = studentController.getUnreadNotifications(userId2, request);

        // Assert
        assertEquals(1, result1.size());
        assertEquals(1, result2.size());
        assertNotEquals(result1.get(0), result2.get(0));
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour avec studentId zéro")
    void testUpdateUserWithZeroId() {
        // Arrange
        int studentId = 0;
        doThrow(new IllegalArgumentException("Student ID must be greater than 0"))
                .when(studentService).save(eq(studentId), any(Student.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = studentController.updateUser(studentId, mockStudent, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("must be greater than 0"));
    }
}