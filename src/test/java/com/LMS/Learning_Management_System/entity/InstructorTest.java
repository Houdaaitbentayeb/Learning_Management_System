package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Instructor Entity Tests")
class InstructorTest {

    private Instructor instructor;
    private Users mockUser;

    @BeforeEach
    void setUp() {
        // Création d'un mock utilisateur pour les tests
        mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setEmail("instructor@test.com");
        mockUser.setPassword("password123");
    }

    @Test
    @DisplayName("Test création Instructor avec constructeur par défaut")
    void testDefaultConstructor() {
        instructor = new Instructor();

        assertNotNull(instructor, "L'instructor ne doit pas être null");
        assertEquals(0, instructor.getUserAccountId(), "L'ID par défaut doit être 0");
        assertNull(instructor.getFirstName(), "Le prénom par défaut doit être null");
        assertNull(instructor.getLastName(), "Le nom par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Instructor avec constructeur complet")
    void testFullConstructor() {
        instructor = new Instructor(1, mockUser, "Ahmed", "Hassan");

        assertNotNull(instructor, "L'instructor ne doit pas être null");
        assertEquals(1, instructor.getUserAccountId(), "L'ID doit être 1");
        assertEquals(mockUser, instructor.getUserId(), "L'utilisateur doit correspondre");
        assertEquals("Ahmed", instructor.getFirstName(), "Le prénom doit être Ahmed");
        assertEquals("Hassan", instructor.getLastName(), "Le nom doit être Hassan");
    }

    @Test
    @DisplayName("Test création Instructor avec constructeur Users")
    void testUserConstructor() {
        instructor = new Instructor(mockUser);

        assertNotNull(instructor, "L'instructor ne doit pas être null");
        assertEquals(mockUser, instructor.getUserId(), "L'utilisateur doit correspondre");
        assertNull(instructor.getFirstName(), "Le prénom doit être null");
        assertNull(instructor.getLastName(), "Le nom doit être null");
    }

    @Test
    @DisplayName("Test setters et getters - UserAccountId")
    void testUserAccountIdSetterGetter() {
        instructor = new Instructor();
        instructor.setUserAccountId(100);

        assertEquals(100, instructor.getUserAccountId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - UserId")
    void testUserIdSetterGetter() {
        instructor = new Instructor();
        instructor.setUserId(mockUser);

        assertEquals(mockUser, instructor.getUserId(), "L'utilisateur doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - FirstName")
    void testFirstNameSetterGetter() {
        instructor = new Instructor();
        instructor.setFirstName("Mohamed");

        assertEquals("Mohamed", instructor.getFirstName(), "Le prénom doit être Mohamed");
    }

    @Test
    @DisplayName("Test setters et getters - LastName")
    void testLastNameSetterGetter() {
        instructor = new Instructor();
        instructor.setLastName("Ali");

        assertEquals("Ali", instructor.getLastName(), "Le nom doit être Ali");
    }

    @Test
    @DisplayName("Test modification du prénom")
    void testModifyFirstName() {
        instructor = new Instructor(1, mockUser, "Ahmed", "Hassan");

        instructor.setFirstName("Mahmoud");

        assertEquals("Mahmoud", instructor.getFirstName(), "Le prénom doit être modifié");
        assertEquals("Hassan", instructor.getLastName(), "Le nom ne doit pas changer");
    }

    @Test
    @DisplayName("Test modification du nom")
    void testModifyLastName() {
        instructor = new Instructor(1, mockUser, "Ahmed", "Hassan");

        instructor.setLastName("Ibrahim");

        assertEquals("Ahmed", instructor.getFirstName(), "Le prénom ne doit pas changer");
        assertEquals("Ibrahim", instructor.getLastName(), "Le nom doit être modifié");
    }

    @Test
    @DisplayName("Test relation OneToOne avec Users")
    void testOneToOneRelationWithUsers() {
        instructor = new Instructor();
        instructor.setUserId(mockUser);

        assertNotNull(instructor.getUserId(), "La relation Users ne doit pas être null");
        assertEquals(mockUser.getUserId(), instructor.getUserId().getUserId(), "L'ID utilisateur doit correspondre");
        assertEquals("instructor@test.com", instructor.getUserId().getEmail(), "L'email doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        instructor = new Instructor(1, mockUser, "Ahmed", "Hassan");

        String result = instructor.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("userAccountId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("firstName='Ahmed'"), "toString doit contenir le prénom");
        assertTrue(result.contains("lastName='Hassan'"), "toString doit contenir le nom");
        assertTrue(result.contains("Instructor{"), "toString doit commencer par Instructor{");
    }

    @Test
    @DisplayName("Test Instructor avec firstName et lastName vides")
    void testInstructorWithEmptyNames() {
        instructor = new Instructor(1, mockUser, "", "");

        assertEquals("", instructor.getFirstName(), "Le prénom vide doit être accepté");
        assertEquals("", instructor.getLastName(), "Le nom vide doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec firstName null et lastName null")
    void testInstructorWithNullNames() {
        instructor = new Instructor(1, mockUser, null, null);

        assertNull(instructor.getFirstName(), "Le prénom null doit être accepté");
        assertNull(instructor.getLastName(), "Le nom null doit être accepté");
    }

    @Test
    @DisplayName("Test modification de l'utilisateur associé")
    void testModifyAssociatedUser() {
        instructor = new Instructor(1, mockUser, "Ahmed", "Hassan");

        Users newUser = new Users();
        newUser.setUserId(2);
        newUser.setEmail("newinstructor@test.com");

        instructor.setUserId(newUser);

        assertEquals(newUser, instructor.getUserId(), "L'utilisateur doit être modifié");
        assertEquals(2, instructor.getUserId().getUserId(), "Le nouvel ID utilisateur doit être 2");
    }

    @Test
    @DisplayName("Test Instructor avec ID négatif")
    void testInstructorWithNegativeId() {
        instructor = new Instructor();
        instructor.setUserAccountId(-1);

        assertEquals(-1, instructor.getUserAccountId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec noms contenant des caractères spéciaux")
    void testInstructorWithSpecialCharactersInNames() {
        instructor = new Instructor(1, mockUser, "Jean-Pierre", "O'Connor");

        assertEquals("Jean-Pierre", instructor.getFirstName(), "Le prénom avec tiret doit être accepté");
        assertEquals("O'Connor", instructor.getLastName(), "Le nom avec apostrophe doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec noms contenant des accents")
    void testInstructorWithAccentsInNames() {
        instructor = new Instructor(1, mockUser, "José", "García");

        assertEquals("José", instructor.getFirstName(), "Le prénom avec accents doit être accepté");
        assertEquals("García", instructor.getLastName(), "Le nom avec accents doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec noms longs")
    void testInstructorWithLongNames() {
        String longFirstName = "Jean-Baptiste-Alexandre";
        String longLastName = "Dupont-Moreau-Lefebvre";

        instructor = new Instructor(1, mockUser, longFirstName, longLastName);

        assertEquals(longFirstName, instructor.getFirstName(), "Le prénom long doit être accepté");
        assertEquals(longLastName, instructor.getLastName(), "Le nom long doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec noms d'une seule lettre")
    void testInstructorWithSingleLetterNames() {
        instructor = new Instructor(1, mockUser, "A", "B");

        assertEquals("A", instructor.getFirstName(), "Le prénom d'une lettre doit être accepté");
        assertEquals("B", instructor.getLastName(), "Le nom d'une lettre doit être accepté");
    }

    @Test
    @DisplayName("Test comparaison de deux instructors")
    void testCompareTwoInstructors() {
        Instructor instructor1 = new Instructor(1, mockUser, "Ahmed", "Hassan");

        Users user2 = new Users();
        user2.setUserId(2);
        Instructor instructor2 = new Instructor(2, user2, "Mohamed", "Ali");

        assertNotEquals(instructor1.getUserAccountId(), instructor2.getUserAccountId(), "Les IDs doivent être différents");
        assertNotEquals(instructor1.getFirstName(), instructor2.getFirstName(), "Les prénoms doivent être différents");
        assertNotEquals(instructor1.getLastName(), instructor2.getLastName(), "Les noms doivent être différents");
    }

    @Test
    @DisplayName("Test Instructor avec userId null")
    void testInstructorWithNullUserId() {
        instructor = new Instructor(1, null, "Ahmed", "Hassan");

        assertNull(instructor.getUserId(), "L'userId null doit être accepté");
        assertEquals("Ahmed", instructor.getFirstName(), "Le prénom doit être préservé");
    }

    @Test
    @DisplayName("Test création instructor via constructeur Users puis ajout des noms")
    void testInstructorCreationWithUserThenNames() {
        instructor = new Instructor(mockUser);

        assertNull(instructor.getFirstName(), "Le prénom doit être null initialement");
        assertNull(instructor.getLastName(), "Le nom doit être null initialement");

        instructor.setFirstName("Ahmed");
        instructor.setLastName("Hassan");

        assertEquals("Ahmed", instructor.getFirstName(), "Le prénom doit être ajouté");
        assertEquals("Hassan", instructor.getLastName(), "Le nom doit être ajouté");
    }

    @Test
    @DisplayName("Test modification complète des informations")
    void testCompleteInformationModification() {
        instructor = new Instructor(1, mockUser, "Ahmed", "Hassan");

        Users newUser = new Users();
        newUser.setUserId(5);
        newUser.setEmail("updated@test.com");

        instructor.setUserAccountId(10);
        instructor.setUserId(newUser);
        instructor.setFirstName("Mohamed");
        instructor.setLastName("Ali");

        assertEquals(10, instructor.getUserAccountId(), "L'ID doit être modifié");
        assertEquals(newUser, instructor.getUserId(), "L'utilisateur doit être modifié");
        assertEquals("Mohamed", instructor.getFirstName(), "Le prénom doit être modifié");
        assertEquals("Ali", instructor.getLastName(), "Le nom doit être modifié");
    }

    @Test
    @DisplayName("Test Instructor avec noms en majuscules")
    void testInstructorWithUppercaseNames() {
        instructor = new Instructor(1, mockUser, "AHMED", "HASSAN");

        assertEquals("AHMED", instructor.getFirstName(), "Le prénom en majuscules doit être accepté");
        assertEquals("HASSAN", instructor.getLastName(), "Le nom en majuscules doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec noms en minuscules")
    void testInstructorWithLowercaseNames() {
        instructor = new Instructor(1, mockUser, "ahmed", "hassan");

        assertEquals("ahmed", instructor.getFirstName(), "Le prénom en minuscules doit être accepté");
        assertEquals("hassan", instructor.getLastName(), "Le nom en minuscules doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec noms mixtes (majuscules et minuscules)")
    void testInstructorWithMixedCaseNames() {
        instructor = new Instructor(1, mockUser, "AhMeD", "HaSsAn");

        assertEquals("AhMeD", instructor.getFirstName(), "Le prénom mixte doit être accepté");
        assertEquals("HaSsAn", instructor.getLastName(), "Le nom mixte doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec espaces dans les noms")
    void testInstructorWithSpacesInNames() {
        instructor = new Instructor(1, mockUser, "Ahmed Ali", "Hassan Mohamed");

        assertEquals("Ahmed Ali", instructor.getFirstName(), "Le prénom avec espaces doit être accepté");
        assertEquals("Hassan Mohamed", instructor.getLastName(), "Le nom avec espaces doit être accepté");
    }

    @Test
    @DisplayName("Test Instructor avec noms contenant des chiffres")
    void testInstructorWithNumbersInNames() {
        instructor = new Instructor(1, mockUser, "Ahmed2", "Hassan3");

        assertEquals("Ahmed2", instructor.getFirstName(), "Le prénom avec chiffres doit être accepté");
        assertEquals("Hassan3", instructor.getLastName(), "Le nom avec chiffres doit être accepté");
    }

    @Test
    @DisplayName("Test vérification de l'identité de l'instructor via userAccountId")
    void testInstructorIdentityViaUserAccountId() {
        instructor = new Instructor(1, mockUser, "Ahmed", "Hassan");

        assertEquals(1, instructor.getUserAccountId(), "L'ID doit identifier l'instructor");
        assertEquals(mockUser.getUserId(), instructor.getUserId().getUserId(),
                "L'ID utilisateur doit correspondre à l'ID du compte");
    }
}