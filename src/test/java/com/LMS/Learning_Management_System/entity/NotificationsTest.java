package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Notifications Entity Tests")
class NotificationsTest {

    private Notifications notification;
    private Users mockUser;
    private Date testCreatedTime;

    @BeforeEach
    void setUp() {
        // Création d'un mock user pour les tests
        mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setEmail("student@test.com");

        // Création d'une date de test
        testCreatedTime = new Date();
    }

    @Test
    @DisplayName("Test création Notification avec constructeur par défaut")
    void testDefaultConstructor() {
        notification = new Notifications();

        assertNotNull(notification, "La notification ne doit pas être null");
        assertEquals(0, notification.getNotificationsId(), "L'ID par défaut doit être 0");
        assertNull(notification.getMessage(), "Le message par défaut doit être null");
        assertFalse(notification.isRead(), "isRead par défaut doit être false");
    }

    @Test
    @DisplayName("Test création Notification avec constructeur complet")
    void testFullConstructor() {
        notification = new Notifications(1, mockUser, "Nouveau cours disponible", testCreatedTime);

        assertNotNull(notification, "La notification ne doit pas être null");
        assertEquals(1, notification.getNotificationsId(), "L'ID doit être 1");
        assertEquals(mockUser, notification.getUserId(), "L'utilisateur doit correspondre");
        assertEquals("Nouveau cours disponible", notification.getMessage(), "Le message doit correspondre");
        assertEquals(testCreatedTime, notification.getCreatedTime(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - NotificationsId")
    void testNotificationsIdSetterGetter() {
        notification = new Notifications();
        notification.setNotificationsId(100);

        assertEquals(100, notification.getNotificationsId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - UserId")
    void testUserIdSetterGetter() {
        notification = new Notifications();
        notification.setUserId(mockUser);

        assertEquals(mockUser, notification.getUserId(), "L'utilisateur doit correspondre");
        assertEquals(1, notification.getUserId().getUserId(), "L'ID de l'utilisateur doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - Message")
    void testMessageSetterGetter() {
        notification = new Notifications();
        notification.setMessage("Votre devoir a été noté");

        assertEquals("Votre devoir a été noté", notification.getMessage(), "Le message doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - CreatedTime")
    void testCreatedTimeSetterGetter() {
        notification = new Notifications();
        notification.setCreatedTime(testCreatedTime);

        assertEquals(testCreatedTime, notification.getCreatedTime(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - Read true")
    void testReadSetterGetterTrue() {
        notification = new Notifications();
        notification.setRead(true);

        assertTrue(notification.isRead(), "isRead doit être true");
    }

    @Test
    @DisplayName("Test setters et getters - Read false")
    void testReadSetterGetterFalse() {
        notification = new Notifications();
        notification.setRead(false);

        assertFalse(notification.isRead(), "isRead doit être false");
    }

    @Test
    @DisplayName("Test création notification non lue")
    void testCreateUnreadNotification() {
        notification = new Notifications(1, mockUser, "Nouveau message", testCreatedTime);

        assertFalse(notification.isRead(), "La notification doit être non lue par défaut");
    }

    @Test
    @DisplayName("Test création notification lue")
    void testCreateReadNotification() {
        notification = new Notifications(1, mockUser, "Message lu", testCreatedTime);
        notification.setRead(true);

        assertTrue(notification.isRead(), "La notification doit être marquée comme lue");
    }

    @Test
    @DisplayName("Test marquer notification comme lue")
    void testMarkNotificationAsRead() {
        notification = new Notifications(1, mockUser, "Message", testCreatedTime);

        assertFalse(notification.isRead(), "La notification doit être non lue initialement");

        notification.setRead(true);

        assertTrue(notification.isRead(), "La notification doit être marquée comme lue");
    }

    @Test
    @DisplayName("Test marquer notification comme non lue")
    void testMarkNotificationAsUnread() {
        notification = new Notifications(1, mockUser, "Message", testCreatedTime);
        notification.setRead(true);

        notification.setRead(false);

        assertFalse(notification.isRead(), "La notification doit être marquée comme non lue");
    }

    @Test
    @DisplayName("Test modification du message")
    void testModifyMessage() {
        notification = new Notifications(1, mockUser, "Message initial", testCreatedTime);

        notification.setMessage("Message modifié");

        assertEquals("Message modifié", notification.getMessage(), "Le message doit être modifié");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Users")
    void testManyToOneRelationWithUsers() {
        notification = new Notifications();
        notification.setUserId(mockUser);

        assertNotNull(notification.getUserId(), "L'utilisateur ne doit pas être null");
        assertEquals(mockUser.getUserId(), notification.getUserId().getUserId(), "L'ID de l'utilisateur doit correspondre");
        assertEquals("student@test.com", notification.getUserId().getEmail(), "L'email doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        notification = new Notifications(1, mockUser, "Test message", testCreatedTime);

        String result = notification.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("notificationsId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("message='Test message'"), "toString doit contenir le message");
        assertTrue(result.contains("Notifications{"), "toString doit commencer par Notifications{");
    }

    @Test
    @DisplayName("Test Notification avec message vide")
    void testNotificationWithEmptyMessage() {
        notification = new Notifications(1, mockUser, "", testCreatedTime);

        assertEquals("", notification.getMessage(), "Le message vide doit être accepté");
    }

    @Test
    @DisplayName("Test Notification avec message null")
    void testNotificationWithNullMessage() {
        notification = new Notifications(1, mockUser, null, testCreatedTime);

        assertNull(notification.getMessage(), "Le message null doit être accepté");
    }

    @Test
    @DisplayName("Test Notification avec createdTime null")
    void testNotificationWithNullCreatedTime() {
        notification = new Notifications(1, mockUser, "Message", null);

        assertNull(notification.getCreatedTime(), "La date null doit être acceptée");
    }

    @Test
    @DisplayName("Test Notification avec message long")
    void testNotificationWithLongMessage() {
        String longMessage = "Ceci est un très long message de notification qui contient beaucoup " +
                "d'informations détaillées sur l'événement qui s'est produit dans le " +
                "système de gestion d'apprentissage et qui nécessite l'attention de l'utilisateur.";
        notification = new Notifications(1, mockUser, longMessage, testCreatedTime);

        assertEquals(longMessage, notification.getMessage(), "Le message long doit être accepté");
        assertTrue(notification.getMessage().length() > 100, "Le message doit être long");
    }

    @Test
    @DisplayName("Test Notification avec date dans le passé")
    void testNotificationWithPastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date pastDate = calendar.getTime();

        notification = new Notifications(1, mockUser, "Message ancien", pastDate);

        assertEquals(pastDate, notification.getCreatedTime(), "La date passée doit être acceptée");
        assertTrue(notification.getCreatedTime().before(new Date()), "La date doit être dans le passé");
    }

    @Test
    @DisplayName("Test Notification avec date actuelle")
    void testNotificationWithCurrentDate() {
        Date currentDate = new Date();
        notification = new Notifications(1, mockUser, "Message récent", currentDate);

        assertNotNull(notification.getCreatedTime(), "La date ne doit pas être null");
    }

    @Test
    @DisplayName("Test modification de la date de création")
    void testModifyCreatedTime() {
        notification = new Notifications(1, mockUser, "Message", testCreatedTime);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -2);
        Date newDate = calendar.getTime();

        notification.setCreatedTime(newDate);

        assertEquals(newDate, notification.getCreatedTime(), "La date doit être modifiée");
        assertNotEquals(testCreatedTime, notification.getCreatedTime(), "La nouvelle date doit être différente");
    }

    @Test
    @DisplayName("Test modification de l'utilisateur associé")
    void testModifyAssociatedUser() {
        notification = new Notifications(1, mockUser, "Message", testCreatedTime);

        Users newUser = new Users();
        newUser.setUserId(2);
        newUser.setEmail("instructor@test.com");

        notification.setUserId(newUser);

        assertEquals(newUser, notification.getUserId(), "L'utilisateur doit être modifié");
        assertEquals(2, notification.getUserId().getUserId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test plusieurs notifications pour le même utilisateur")
    void testMultipleNotificationsForSameUser() {
        Notifications notif1 = new Notifications(1, mockUser, "Message 1", testCreatedTime);
        Notifications notif2 = new Notifications(2, mockUser, "Message 2", testCreatedTime);

        assertEquals(mockUser, notif1.getUserId(), "Les deux notifications doivent avoir le même utilisateur");
        assertEquals(mockUser, notif2.getUserId(), "Les deux notifications doivent avoir le même utilisateur");
        assertNotEquals(notif1.getNotificationsId(), notif2.getNotificationsId(), "Les IDs doivent être différents");
    }

    @Test
    @DisplayName("Test Notification avec caractères spéciaux dans le message")
    void testNotificationWithSpecialCharactersInMessage() {
        String specialMessage = "Votre note: 85% - Félicitations! 🎉";
        notification = new Notifications(1, mockUser, specialMessage, testCreatedTime);

        assertEquals(specialMessage, notification.getMessage(), "Les caractères spéciaux doivent être acceptés");
    }

    @Test
    @DisplayName("Test Notification avec sauts de ligne dans le message")
    void testNotificationWithLineBreaksInMessage() {
        String messageWithBreaks = "Ligne 1\nLigne 2\nLigne 3";
        notification = new Notifications(1, mockUser, messageWithBreaks, testCreatedTime);

        assertEquals(messageWithBreaks, notification.getMessage(), "Les sauts de ligne doivent être acceptés");
        assertTrue(notification.getMessage().contains("\n"), "Le message doit contenir des sauts de ligne");
    }

    @Test
    @DisplayName("Test Notification de type cours disponible")
    void testCourseAvailableNotification() {
        notification = new Notifications(1, mockUser, "Nouveau cours: Spring Boot Avancé disponible", testCreatedTime);

        assertTrue(notification.getMessage().contains("Nouveau cours"), "Le message doit mentionner un nouveau cours");
        assertFalse(notification.isRead(), "La notification doit être non lue");
    }

    @Test
    @DisplayName("Test Notification de type devoir noté")
    void testAssignmentGradedNotification() {
        notification = new Notifications(1, mockUser, "Votre devoir 'TP SonarQube' a été noté: 18/20", testCreatedTime);

        assertTrue(notification.getMessage().contains("noté"), "Le message doit mentionner la notation");
        assertFalse(notification.isRead(), "La notification doit être non lue");
    }

    @Test
    @DisplayName("Test Notification de type rappel")
    void testReminderNotification() {
        notification = new Notifications(1, mockUser, "Rappel: Quiz final demain à 10h", testCreatedTime);

        assertTrue(notification.getMessage().contains("Rappel"), "Le message doit être un rappel");
    }

    @Test
    @DisplayName("Test comparaison de deux notifications")
    void testCompareNotifications() {
        Notifications notif1 = new Notifications(1, mockUser, "Message 1", testCreatedTime);
        Notifications notif2 = new Notifications(2, mockUser, "Message 2", testCreatedTime);

        assertNotEquals(notif1.getNotificationsId(), notif2.getNotificationsId(), "Les IDs doivent être différents");
        assertNotEquals(notif1.getMessage(), notif2.getMessage(), "Les messages doivent être différents");
    }

    @Test
    @DisplayName("Test tri de notifications par date")
    void testSortNotificationsByDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date date1 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date2 = calendar.getTime();

        Notifications notif1 = new Notifications(1, mockUser, "Message ancien", date1);
        Notifications notif2 = new Notifications(2, mockUser, "Message récent", date2);

        assertTrue(notif1.getCreatedTime().before(notif2.getCreatedTime()),
                "La première notification doit être plus ancienne");
    }

    @Test
    @DisplayName("Test Notification avec ID négatif")
    void testNotificationWithNegativeId() {
        notification = new Notifications();
        notification.setNotificationsId(-1);

        assertEquals(-1, notification.getNotificationsId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test changement d'état multiple de read")
    void testMultipleReadStateChanges() {
        notification = new Notifications(1, mockUser, "Message", testCreatedTime);

        assertFalse(notification.isRead(), "Initialement non lu");

        notification.setRead(true);
        assertTrue(notification.isRead(), "Marqué comme lu");

        notification.setRead(false);
        assertFalse(notification.isRead(), "Marqué comme non lu");

        notification.setRead(true);
        assertTrue(notification.isRead(), "Re-marqué comme lu");
    }

    @Test
    @DisplayName("Test Notification avec message HTML")
    void testNotificationWithHTMLMessage() {
        String htmlMessage = "<strong>Important:</strong> Votre cours commence dans <em>10 minutes</em>";
        notification = new Notifications(1, mockUser, htmlMessage, testCreatedTime);

        assertEquals(htmlMessage, notification.getMessage(), "Le message HTML doit être accepté");
        assertTrue(notification.getMessage().contains("<strong>"), "Le message doit contenir des balises HTML");
    }
}