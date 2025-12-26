package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Student Entity Tests")
class StudentTest {

    private Student student;
    private Users mockUser;

    @BeforeEach
    void setUp() {
        // Création d'un utilisateur mock
        mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setEmail("student@test.com");
        mockUser.setPassword("password123");
    }

    @Test
    @DisplayName("Test création Student avec constructeur par défaut")
    void testDefaultConstructor() {
        student = new Student();

        assertNotNull(student, "Le student ne doit pas être null");
        assertEquals(0, student.getUserAccountId(), "L'ID par défaut doit être 0");
        assertNull(student.getUserId(), "L'utilisateur doit être null");
        assertNull(student.getFirstName(), "Le prénom doit être null");
        assertNull(student.getLastName(), "Le nom doit être null");
    }

    @Test
    @DisplayName("Test création Student avec constructeur complet")
    void testFullConstructor() {
        student = new Student(1, mockUser, "Sara", "Benali");

        assertNotNull(student, "Le student ne doit pas être null");
        assertEquals(1, student.getUserAccountId(), "L'ID doit être 1");
        assertEquals(mockUser, student.getUserId(), "L'utilisateur doit correspondre");
        assertEquals("Sara", student.getFirstName(), "Le prénom doit être Sara");
        assertEquals("Benali", student.getLastName(), "Le nom doit être Benali");
    }

    @Test
    @DisplayName("Test création Student avec constructeur Users")
    void testUserConstructor() {
        student = new Student(mockUser);

        assertNotNull(student, "Le student ne doit pas être null");
        assertEquals(mockUser, student.getUserId(), "L'utilisateur doit correspondre");
        assertNull(student.getFirstName(), "Le prénom doit être null");
        assertNull(student.getLastName(), "Le nom doit être null");
    }

    @Test
    @DisplayName("Test setters et getters - UserAccountId")
    void testUserAccountIdSetterGetter() {
        student = new Student();
        student.setUserAccountId(10);

        assertEquals(10, student.getUserAccountId(), "L'ID doit être 10");
    }

    @Test
    @DisplayName("Test setters et getters - UserId")
    void testUserIdSetterGetter() {
        student = new Student();
        student.setUserId(mockUser);

        assertEquals(mockUser, student.getUserId(), "L'utilisateur doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - FirstName")
    void testFirstNameSetterGetter() {
        student = new Student();
        student.setFirstName("Yassine");

        assertEquals("Yassine", student.getFirstName(), "Le prénom doit être Yassine");
    }

    @Test
    @DisplayName("Test setters et getters - LastName")
    void testLastNameSetterGetter() {
        student = new Student();
        student.setLastName("El Amrani");

        assertEquals("El Amrani", student.getLastName(), "Le nom doit être El Amrani");
    }

    @Test
    @DisplayName("Test modification FirstName")
    void testModifyFirstName() {
        student = new Student(1, mockUser, "Sara", "Benali");

        student.setFirstName("Salma");

        assertEquals("Salma", student.getFirstName(), "Le prénom doit être modifié");
        assertEquals("Benali", student.getLastName(), "Le nom ne doit pas changer");
    }

    @Test
    @DisplayName("Test modification LastName")
    void testModifyLastName() {
        student = new Student(1, mockUser, "Sara", "Benali");

        student.setLastName("Alaoui");

        assertEquals("Sara", student.getFirstName(), "Le prénom ne doit pas changer");
        assertEquals("Alaoui", student.getLastName(), "Le nom doit être modifié");
    }

    @Test
    @DisplayName("Test relation OneToOne avec Users")
    void testOneToOneRelationWithUsers() {
        student = new Student();
        student.setUserId(mockUser);

        assertNotNull(student.getUserId(), "La relation Users ne doit pas être null");
        assertEquals(mockUser.getUserId(),
                student.getUserId().getUserId(),
                "L'ID utilisateur doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        student = new Student(1, mockUser, "Sara", "Benali");

        String result = student.toString();

        assertNotNull(result, "toString ne doit pas retourner null");

        // robuste : pas de dépendance au format "firstName='...'"
        assertTrue(result.contains("Student"), "toString doit contenir le nom de la classe");
        assertTrue(result.contains("1"), "toString doit contenir l'ID (1)");
        assertTrue(result.contains("Sara"), "toString doit contenir le prénom");
        assertTrue(result.contains("Benali"), "toString doit contenir le nom");
    }


    @Test
    @DisplayName("Test Student avec firstName et lastName vides")
    void testStudentWithEmptyNames() {
        student = new Student(1, mockUser, "", "");

        assertEquals("", student.getFirstName(), "Le prénom vide doit être accepté");
        assertEquals("", student.getLastName(), "Le nom vide doit être accepté");
    }

    @Test
    @DisplayName("Test Student avec firstName et lastName null")
    void testStudentWithNullNames() {
        student = new Student(1, mockUser, null, null);

        assertNull(student.getFirstName(), "Le prénom null doit être accepté");
        assertNull(student.getLastName(), "Le nom null doit être accepté");
    }

    @Test
    @DisplayName("Test modification de l'utilisateur associé")
    void testModifyAssociatedUser() {
        student = new Student(1, mockUser, "Sara", "Benali");

        Users newUser = new Users();
        newUser.setUserId(2);
        newUser.setEmail("newstudent@test.com");

        student.setUserId(newUser);

        assertEquals(newUser, student.getUserId(), "L'utilisateur doit être modifié");
        assertEquals(2, student.getUserId().getUserId(), "Le nouvel ID utilisateur doit être 2");
    }

    @Test
    @DisplayName("Test Student avec ID négatif")
    void testStudentWithNegativeId() {
        student = new Student();
        student.setUserAccountId(-5);

        assertEquals(-5, student.getUserAccountId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test Student avec caractères spéciaux dans les noms")
    void testStudentWithSpecialCharactersInNames() {
        student = new Student(1, mockUser, "Jean-Luc", "O'Neill");

        assertEquals("Jean-Luc", student.getFirstName(), "Le prénom avec tiret doit être accepté");
        assertEquals("O'Neill", student.getLastName(), "Le nom avec apostrophe doit être accepté");
    }
}
