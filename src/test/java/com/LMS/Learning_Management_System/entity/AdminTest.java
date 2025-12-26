package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Admin Entity Tests")
class AdminTest {

    private Admin admin;
    private Users mockUser;

    @BeforeEach
    void setUp() {
        // Création d'un mock utilisateur pour les tests
        mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setEmail("admin@test.com");
        mockUser.setPassword("password123");
    }

    @Test
    @DisplayName("Test création Admin avec constructeur par défaut")
    void testDefaultConstructor() {
        admin = new Admin();

        assertNotNull(admin, "L'admin ne doit pas être null");
        assertEquals(0, admin.getUserAccountId(), "L'ID par défaut doit être 0");
        assertNull(admin.getFirstName(), "Le prénom par défaut doit être null");
        assertNull(admin.getLastName(), "Le nom par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Admin avec constructeur complet")
    void testFullConstructor() {
        admin = new Admin(1, mockUser, "Ahmed", "Ali");

        assertNotNull(admin, "L'admin ne doit pas être null");
        assertEquals(1, admin.getUserAccountId(), "L'ID doit être 1");
        assertEquals(mockUser, admin.getUserId(), "L'utilisateur doit correspondre");
        assertEquals("Ahmed", admin.getFirstName(), "Le prénom doit être Ahmed");
        assertEquals("Ali", admin.getLastName(), "Le nom doit être Ali");
    }

    @Test
    @DisplayName("Test création Admin avec constructeur Users")
    void testUserConstructor() {
        admin = new Admin(mockUser);

        assertNotNull(admin, "L'admin ne doit pas être null");
        assertEquals(mockUser, admin.getUserId(), "L'utilisateur doit correspondre");
        assertNull(admin.getFirstName(), "Le prénom doit être null");
        assertNull(admin.getLastName(), "Le nom doit être null");
    }

    @Test
    @DisplayName("Test setters et getters - UserAccountId")
    void testUserAccountIdSetterGetter() {
        admin = new Admin();
        admin.setUserAccountId(100);

        assertEquals(100, admin.getUserAccountId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - UserId")
    void testUserIdSetterGetter() {
        admin = new Admin();
        admin.setUserId(mockUser);

        assertEquals(mockUser, admin.getUserId(), "L'utilisateur doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - FirstName")
    void testFirstNameSetterGetter() {
        admin = new Admin();
        admin.setFirstName("Mohamed");

        assertEquals("Mohamed", admin.getFirstName(), "Le prénom doit être Mohamed");
    }

    @Test
    @DisplayName("Test setters et getters - LastName")
    void testLastNameSetterGetter() {
        admin = new Admin();
        admin.setLastName("Hassan");

        assertEquals("Hassan", admin.getLastName(), "Le nom doit être Hassan");
    }

    @Test
    @DisplayName("Test modification FirstName")
    void testModifyFirstName() {
        admin = new Admin(1, mockUser, "Ahmed", "Ali");

        admin.setFirstName("Mahmoud");

        assertEquals("Mahmoud", admin.getFirstName(), "Le prénom doit être modifié");
        assertEquals("Ali", admin.getLastName(), "Le nom ne doit pas changer");
    }

    @Test
    @DisplayName("Test modification LastName")
    void testModifyLastName() {
        admin = new Admin(1, mockUser, "Ahmed", "Ali");

        admin.setLastName("Ibrahim");

        assertEquals("Ahmed", admin.getFirstName(), "Le prénom ne doit pas changer");
        assertEquals("Ibrahim", admin.getLastName(), "Le nom doit être modifié");
    }

    @Test
    @DisplayName("Test relation OneToOne avec Users")
    void testOneToOneRelationWithUsers() {
        admin = new Admin();
        admin.setUserId(mockUser);

        assertNotNull(admin.getUserId(), "La relation Users ne doit pas être null");
        assertEquals(mockUser.getUserId(), admin.getUserId().getUserId(), "L'ID utilisateur doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        admin = new Admin(1, mockUser, "Ahmed", "Ali");

        String result = admin.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("userAccountId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("firstName='Ahmed'"), "toString doit contenir le prénom");
        assertTrue(result.contains("lastName='Ali'"), "toString doit contenir le nom");
        assertTrue(result.contains("Admin{"), "toString doit commencer par Admin{");
    }

    @Test
    @DisplayName("Test Admin avec firstName et lastName vides")
    void testAdminWithEmptyNames() {
        admin = new Admin(1, mockUser, "", "");

        assertEquals("", admin.getFirstName(), "Le prénom vide doit être accepté");
        assertEquals("", admin.getLastName(), "Le nom vide doit être accepté");
    }

    @Test
    @DisplayName("Test Admin avec firstName null et lastName null")
    void testAdminWithNullNames() {
        admin = new Admin(1, mockUser, null, null);

        assertNull(admin.getFirstName(), "Le prénom null doit être accepté");
        assertNull(admin.getLastName(), "Le nom null doit être accepté");
    }

    @Test
    @DisplayName("Test modification de l'utilisateur associé")
    void testModifyAssociatedUser() {
        admin = new Admin(1, mockUser, "Ahmed", "Ali");

        Users newUser = new Users();
        newUser.setUserId(2);
        newUser.setEmail("newadmin@test.com");

        admin.setUserId(newUser);

        assertEquals(newUser, admin.getUserId(), "L'utilisateur doit être modifié");
        assertEquals(2, admin.getUserId().getUserId(), "Le nouvel ID utilisateur doit être 2");
    }

    @Test
    @DisplayName("Test Admin avec ID négatif")
    void testAdminWithNegativeId() {
        admin = new Admin();
        admin.setUserAccountId(-1);

        assertEquals(-1, admin.getUserAccountId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test Admin avec noms contenant des caractères spéciaux")
    void testAdminWithSpecialCharactersInNames() {
        admin = new Admin(1, mockUser, "Jean-Pierre", "O'Connor");

        assertEquals("Jean-Pierre", admin.getFirstName(), "Le prénom avec tiret doit être accepté");
        assertEquals("O'Connor", admin.getLastName(), "Le nom avec apostrophe doit être accepté");
    }
}