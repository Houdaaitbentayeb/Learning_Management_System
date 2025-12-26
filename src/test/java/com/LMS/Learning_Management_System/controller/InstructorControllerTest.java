package com.LMS.Learning_Management_System.controller;

import com.LMS.Learning_Management_System.entity.Instructor;
import com.LMS.Learning_Management_System.service.InstructorService;
import com.LMS.Learning_Management_System.service.NotificationsService;
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
@DisplayName("InstructorController Tests")
class InstructorControllerTest {

    @Mock
    private InstructorService instructorService;

    @Mock
    private NotificationsService notificationsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private InstructorController instructorController;

    private Instructor mockInstructor;
    private List<String> mockNotifications;

    @BeforeEach
    void setUp() {
        // Configuration du mock Instructor
        mockInstructor = new Instructor();
        mockInstructor.setUserAccountId(1);
        mockInstructor.setFirstName("Ahmed");
        mockInstructor.setLastName("Hassan");

        // Configuration des notifications mock
        mockNotifications = Arrays.asList(
                "Notification 1: Nouvel étudiant inscrit",
                "Notification 2: Devoir soumis",
                "Notification 3: Question posée dans le forum"
        );
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour réussie")
    void testUpdateUserSuccess() {
        // Arrange
        int instructorId = 1;
        doNothing().when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Instructor updated successfully.", response.getBody());
        verify(instructorService, times(1)).save(instructorId, mockInstructor, request);
    }

    @Test
    @DisplayName("Test updateUser - Échec avec IllegalArgumentException")
    void testUpdateUserFailure() {
        // Arrange
        int instructorId = 1;
        doThrow(new IllegalArgumentException("Instructor not found"))
                .when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Instructor not found", response.getBody());
    }

    @Test
    @DisplayName("Test getAllNotifications - Récupération réussie")
    void testGetAllNotificationsSuccess() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        List<String> notifications = instructorController.getAllNotifications(userId, request);

        // Assert
        assertNotNull(notifications);
        assertEquals(3, notifications.size());
        assertEquals("Notification 1: Nouvel étudiant inscrit", notifications.get(0));
        verify(notificationsService, times(1)).getAllNotifications(userId, request);
    }

    @Test
    @DisplayName("Test getAllNotifications - Liste vide")
    void testGetAllNotificationsEmpty() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(Collections.emptyList());

        // Act
        List<String> notifications = instructorController.getAllNotifications(userId, request);

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
        List<String> notifications = instructorController.getUnreadNotifications(userId, request);

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
        List<String> notifications = instructorController.getUnreadNotifications(userId, request);

        // Assert
        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
        verify(notificationsService, times(1)).getAllUnreadNotifications(userId, request);
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour du prénom")
    void testUpdateUserFirstName() {
        // Arrange
        int instructorId = 1;
        mockInstructor.setFirstName("Mohamed");
        doNothing().when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mohamed", mockInstructor.getFirstName());
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour du nom")
    void testUpdateUserLastName() {
        // Arrange
        int instructorId = 1;
        mockInstructor.setLastName("Ali");
        doNothing().when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ali", mockInstructor.getLastName());
    }

    @Test
    @DisplayName("Test updateUser - InstructorId invalide")
    void testUpdateUserInvalidId() {
        // Arrange
        int invalidInstructorId = -1;
        doThrow(new IllegalArgumentException("Invalid instructor ID"))
                .when(instructorService).save(eq(invalidInstructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(invalidInstructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid instructor ID", response.getBody());
    }

    @Test
    @DisplayName("Test updateUser - Instructor null")
    void testUpdateUserNullInstructor() {
        // Arrange
        int instructorId = 1;
        doThrow(new IllegalArgumentException("Instructor cannot be null"))
                .when(instructorService).save(eq(instructorId), isNull(), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, null, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Instructor cannot be null", response.getBody());
    }

    @Test
    @DisplayName("Test getAllNotifications - UserId invalide")
    void testGetAllNotificationsInvalidUserId() {
        // Arrange
        int invalidUserId = -1;
        when(notificationsService.getAllNotifications(invalidUserId, request))
                .thenReturn(Collections.emptyList());

        // Act
        List<String> notifications = instructorController.getAllNotifications(invalidUserId, request);

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
        List<String> notifications = instructorController.getUnreadNotifications(invalidUserId, request);

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
        List<String> notifications = instructorController.getAllNotifications(userId, request);

        // Assert
        assertEquals("Notification 1: Nouvel étudiant inscrit", notifications.get(0));
        assertEquals("Notification 2: Devoir soumis", notifications.get(1));
        assertEquals("Notification 3: Question posée dans le forum", notifications.get(2));
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour multiple des champs")
    void testUpdateUserMultipleFields() {
        // Arrange
        int instructorId = 1;
        mockInstructor.setFirstName("NewFirstName");
        mockInstructor.setLastName("NewLastName");
        doNothing().when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("NewFirstName", mockInstructor.getFirstName());
        assertEquals("NewLastName", mockInstructor.getLastName());
    }

    @Test
    @DisplayName("Test getAllNotifications puis getUnreadNotifications - Comparaison")
    void testCompareAllAndUnreadNotifications() {
        // Arrange
        int userId = 1;
        List<String> unreadNotifications = Arrays.asList(
                "Notification 1: Nouvel étudiant inscrit"
        );
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);
        when(notificationsService.getAllUnreadNotifications(userId, request)).thenReturn(unreadNotifications);

        // Act
        List<String> allNotifications = instructorController.getAllNotifications(userId, request);
        List<String> unread = instructorController.getUnreadNotifications(userId, request);

        // Assert
        assertEquals(3, allNotifications.size());
        assertEquals(1, unread.size());
        assertTrue(allNotifications.size() > unread.size());
    }

    @Test
    @DisplayName("Test updateUser - Instructor existant non trouvé")
    void testUpdateUserNotFound() {
        // Arrange
        int instructorId = 999;
        doThrow(new IllegalArgumentException("Instructor with ID 999 not found"))
                .when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

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
        instructorController.getAllNotifications(userId, request);

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
        instructorController.getUnreadNotifications(userId, request);

        // Assert
        verify(notificationsService, times(1)).getAllUnreadNotifications(userId, request);
    }

    @Test
    @DisplayName("Test updateUser - Vérification des appels au service")
    void testUpdateUserServiceCallVerification() {
        // Arrange
        int instructorId = 1;
        doNothing().when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        verify(instructorService, times(1)).save(instructorId, mockInstructor, request);
        verify(instructorService, only()).save(instructorId, mockInstructor, request);
    }

    @Test
    @DisplayName("Test getAllNotifications - Plusieurs appels consécutifs")
    void testGetAllNotificationsMultipleCalls() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        List<String> notifications1 = instructorController.getAllNotifications(userId, request);
        List<String> notifications2 = instructorController.getAllNotifications(userId, request);

        // Assert
        assertEquals(notifications1.size(), notifications2.size());
        verify(notificationsService, times(2)).getAllNotifications(userId, request);
    }

    @Test
    @DisplayName("Test getUnreadNotifications - Plusieurs instructeurs")
    void testGetUnreadNotificationsMultipleInstructors() {
        // Arrange
        int userId1 = 1;
        int userId2 = 2;
        List<String> notifications1 = Arrays.asList("Notification instructor 1");
        List<String> notifications2 = Arrays.asList("Notification instructor 2");

        when(notificationsService.getAllUnreadNotifications(userId1, request)).thenReturn(notifications1);
        when(notificationsService.getAllUnreadNotifications(userId2, request)).thenReturn(notifications2);

        // Act
        List<String> result1 = instructorController.getUnreadNotifications(userId1, request);
        List<String> result2 = instructorController.getUnreadNotifications(userId2, request);

        // Assert
        assertEquals(1, result1.size());
        assertEquals(1, result2.size());
        assertNotEquals(result1.get(0), result2.get(0));
    }

    @Test
    @DisplayName("Test updateUser - Mise à jour avec instructorId zéro")
    void testUpdateUserWithZeroId() {
        // Arrange
        int instructorId = 0;
        doThrow(new IllegalArgumentException("Instructor ID must be greater than 0"))
                .when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("must be greater than 0"));
    }

    @Test
    @DisplayName("Test getAllNotifications - Notifications de types différents")
    void testGetAllNotificationsDifferentTypes() {
        // Arrange
        int userId = 1;
        List<String> mixedNotifications = Arrays.asList(
                "Étudiant inscrit au cours",
                "Devoir soumis par un étudiant",
                "Question dans le forum",
                "Nouvelle évaluation créée"
        );
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mixedNotifications);

        // Act
        List<String> notifications = instructorController.getAllNotifications(userId, request);

        // Assert
        assertEquals(4, notifications.size());
        assertTrue(notifications.stream().anyMatch(n -> n.contains("inscrit")));
        assertTrue(notifications.stream().anyMatch(n -> n.contains("soumis")));
    }

    @Test
    @DisplayName("Test getUnreadNotifications - Toutes les notifications sont lues")
    void testGetUnreadNotificationsAllRead() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllUnreadNotifications(userId, request))
                .thenReturn(Collections.emptyList());

        // Act
        List<String> notifications = instructorController.getUnreadNotifications(userId, request);

        // Assert
        assertTrue(notifications.isEmpty());
        verify(notificationsService, times(1)).getAllUnreadNotifications(userId, request);
    }

    @Test
    @DisplayName("Test updateUser - Informations personnelles complètes")
    void testUpdateUserCompleteInformation() {
        // Arrange
        int instructorId = 1;
        mockInstructor.setFirstName("Dr. Ahmed");
        mockInstructor.setLastName("Hassan Al-Mansouri");
        doNothing().when(instructorService).save(eq(instructorId), any(Instructor.class), any(HttpServletRequest.class));

        // Act
        ResponseEntity<String> response = instructorController.updateUser(instructorId, mockInstructor, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dr. Ahmed", mockInstructor.getFirstName());
        assertEquals("Hassan Al-Mansouri", mockInstructor.getLastName());
    }

    @Test
    @DisplayName("Test getAllNotifications - Vérification du contenu des notifications")
    void testGetAllNotificationsContent() {
        // Arrange
        int userId = 1;
        when(notificationsService.getAllNotifications(userId, request)).thenReturn(mockNotifications);

        // Act
        List<String> notifications = instructorController.getAllNotifications(userId, request);

        // Assert
        assertTrue(notifications.stream().allMatch(n -> n.startsWith("Notification")));
        assertTrue(notifications.stream().anyMatch(n -> n.contains("étudiant")));
        assertTrue(notifications.stream().anyMatch(n -> n.contains("Devoir")));
    }
}