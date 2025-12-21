package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Assignment Entity Tests")
class AssignmentTest {

    private Assignment assignment;
    private Course mockCourse;
    private Date testDueDate;

    @BeforeEach
    void setUp() {
        // Création d'un mock course pour les tests
        mockCourse = new Course();
        mockCourse.setCourseId(1);
        mockCourse.setCourseName("Spring Boot Avancé");

        // Création d'une date de test (dans 7 jours)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        testDueDate = calendar.getTime();
    }

    @Test
    @DisplayName("Test création Assignment avec constructeur par défaut")
    void testDefaultConstructor() {
        assignment = new Assignment();

        assertNotNull(assignment, "L'assignment ne doit pas être null");
        assertEquals(0, assignment.getAssignmentId(), "L'ID par défaut doit être 0");
        assertNull(assignment.getTitle(), "Le titre par défaut doit être null");
        assertNull(assignment.getDescription(), "La description par défaut doit être null");
        assertNull(assignment.getDueDate(), "La date limite par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Assignment avec constructeur complet")
    void testFullConstructor() {
        assignment = new Assignment(
                1,
                "TP Spring Boot",
                "Créer une API REST complète",
                mockCourse,
                testDueDate
        );

        assertNotNull(assignment, "L'assignment ne doit pas être null");
        assertEquals(1, assignment.getAssignmentId(), "L'ID doit être 1");
        assertEquals("TP Spring Boot", assignment.getTitle(), "Le titre doit correspondre");
        assertEquals("Créer une API REST complète", assignment.getDescription(), "La description doit correspondre");
        assertEquals(mockCourse, assignment.getCourseID(), "Le cours doit correspondre");
        assertEquals(testDueDate, assignment.getDueDate(), "La date limite doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - AssignmentId")
    void testAssignmentIdSetterGetter() {
        assignment = new Assignment();
        assignment.setAssignmentId(100);

        assertEquals(100, assignment.getAssignmentId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - Title")
    void testTitleSetterGetter() {
        assignment = new Assignment();
        assignment.setTitle("Mini Projet SonarQube");

        assertEquals("Mini Projet SonarQube", assignment.getTitle(), "Le titre doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - Description")
    void testDescriptionSetterGetter() {
        assignment = new Assignment();
        String description = "Analyser la qualité de code avec SonarQube";
        assignment.setDescription(description);

        assertEquals(description, assignment.getDescription(), "La description doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - CourseID")
    void testCourseIdSetterGetter() {
        assignment = new Assignment();
        assignment.setCourseID(mockCourse);

        assertEquals(mockCourse, assignment.getCourseID(), "Le cours doit correspondre");
        assertEquals(1, assignment.getCourseID().getCourseId(), "L'ID du cours doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - DueDate")
    void testDueDateSetterGetter() {
        assignment = new Assignment();
        assignment.setDueDate(testDueDate);

        assertEquals(testDueDate, assignment.getDueDate(), "La date limite doit correspondre");
    }

    @Test
    @DisplayName("Test modification du titre")
    void testModifyTitle() {
        assignment = new Assignment(1, "Titre initial", "Description", mockCourse, testDueDate);

        assignment.setTitle("Titre modifié");

        assertEquals("Titre modifié", assignment.getTitle(), "Le titre doit être modifié");
        assertEquals("Description", assignment.getDescription(), "La description ne doit pas changer");
    }

    @Test
    @DisplayName("Test modification de la description")
    void testModifyDescription() {
        assignment = new Assignment(1, "Titre", "Description initiale", mockCourse, testDueDate);

        assignment.setDescription("Nouvelle description détaillée");

        assertEquals("Nouvelle description détaillée", assignment.getDescription(), "La description doit être modifiée");
        assertEquals("Titre", assignment.getTitle(), "Le titre ne doit pas changer");
    }

    @Test
    @DisplayName("Test modification de la date limite")
    void testModifyDueDate() {
        assignment = new Assignment(1, "Titre", "Description", mockCourse, testDueDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        Date newDueDate = calendar.getTime();

        assignment.setDueDate(newDueDate);

        assertEquals(newDueDate, assignment.getDueDate(), "La date limite doit être modifiée");
        assertNotEquals(testDueDate, assignment.getDueDate(), "La nouvelle date doit être différente");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Course")
    void testManyToOneRelationWithCourse() {
        assignment = new Assignment();
        assignment.setCourseID(mockCourse);

        assertNotNull(assignment.getCourseID(), "Le cours ne doit pas être null");
        assertEquals(mockCourse.getCourseId(), assignment.getCourseID().getCourseId(), "L'ID du cours doit correspondre");
        assertEquals("Spring Boot Avancé", assignment.getCourseID().getCourseName(), "Le nom du cours doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        assignment = new Assignment(1, "TP JUnit", "Tests unitaires", mockCourse, testDueDate);

        String result = assignment.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("assignmentId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("title='TP JUnit'"), "toString doit contenir le titre");
        assertTrue(result.contains("description='Tests unitaires'"), "toString doit contenir la description");
        assertTrue(result.contains("Assignment{"), "toString doit commencer par Assignment{");
    }

    @Test
    @DisplayName("Test Assignment avec titre vide")
    void testAssignmentWithEmptyTitle() {
        assignment = new Assignment(1, "", "Description", mockCourse, testDueDate);

        assertEquals("", assignment.getTitle(), "Le titre vide doit être accepté");
        assertNotNull(assignment.getDescription(), "La description ne doit pas être null");
    }

    @Test
    @DisplayName("Test Assignment avec description vide")
    void testAssignmentWithEmptyDescription() {
        assignment = new Assignment(1, "Titre", "", mockCourse, testDueDate);

        assertEquals("", assignment.getDescription(), "La description vide doit être acceptée");
        assertNotNull(assignment.getTitle(), "Le titre ne doit pas être null");
    }

    @Test
    @DisplayName("Test Assignment avec titre null")
    void testAssignmentWithNullTitle() {
        assignment = new Assignment(1, null, "Description", mockCourse, testDueDate);

        assertNull(assignment.getTitle(), "Le titre null doit être accepté");
    }

    @Test
    @DisplayName("Test Assignment avec description null")
    void testAssignmentWithNullDescription() {
        assignment = new Assignment(1, "Titre", null, mockCourse, testDueDate);

        assertNull(assignment.getDescription(), "La description null doit être acceptée");
    }

    @Test
    @DisplayName("Test Assignment avec date limite dans le passé")
    void testAssignmentWithPastDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date pastDate = calendar.getTime();

        assignment = new Assignment(1, "Titre", "Description", mockCourse, pastDate);

        assertEquals(pastDate, assignment.getDueDate(), "La date passée doit être acceptée");
        assertTrue(assignment.getDueDate().before(new Date()), "La date doit être dans le passé");
    }

    @Test
    @DisplayName("Test Assignment avec date limite dans le futur")
    void testAssignmentWithFutureDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3);
        Date futureDate = calendar.getTime();

        assignment = new Assignment(1, "Titre", "Description", mockCourse, futureDate);

        assertEquals(futureDate, assignment.getDueDate(), "La date future doit être acceptée");
        assertTrue(assignment.getDueDate().after(new Date()), "La date doit être dans le futur");
    }

    @Test
    @DisplayName("Test Assignment avec date limite aujourd'hui")
    void testAssignmentWithTodayDueDate() {
        Date today = new Date();
        assignment = new Assignment(1, "Titre", "Description", mockCourse, today);

        assertNotNull(assignment.getDueDate(), "La date ne doit pas être null");
    }

    @Test
    @DisplayName("Test modification du cours associé")
    void testModifyAssociatedCourse() {
        assignment = new Assignment(1, "Titre", "Description", mockCourse, testDueDate);

        Course newCourse = new Course();
        newCourse.setCourseId(2);
        newCourse.setCourseName("Nouveau cours");

        assignment.setCourseID(newCourse);

        assertEquals(newCourse, assignment.getCourseID(), "Le cours doit être modifié");
        assertEquals(2, assignment.getCourseID().getCourseId(), "Le nouvel ID doit être 2");
        assertEquals("Nouveau cours", assignment.getCourseID().getCourseName(), "Le nouveau nom doit correspondre");
    }

    @Test
    @DisplayName("Test Assignment avec description longue")
    void testAssignmentWithLongDescription() {
        String longDescription = "Ceci est une très longue description qui contient de nombreux détails " +
                "sur l'assignment à réaliser. Elle inclut les objectifs, les étapes à suivre, " +
                "les critères d'évaluation, et toutes les informations nécessaires pour que " +
                "l'étudiant puisse compléter le travail demandé avec succès.";

        assignment = new Assignment(1, "Titre", longDescription, mockCourse, testDueDate);

        assertEquals(longDescription, assignment.getDescription(), "La description longue doit être acceptée");
        assertTrue(assignment.getDescription().length() > 100, "La description doit être longue");
    }

    @Test
    @DisplayName("Test Assignment avec titre contenant des caractères spéciaux")
    void testAssignmentWithSpecialCharactersInTitle() {
        String specialTitle = "TP #1: API REST & CRUD (évaluation 20%)";
        assignment = new Assignment(1, specialTitle, "Description", mockCourse, testDueDate);

        assertEquals(specialTitle, assignment.getTitle(), "Les caractères spéciaux doivent être acceptés");
    }

    @Test
    @DisplayName("Test plusieurs assignments pour le même cours")
    void testMultipleAssignmentsForSameCourse() {
        Assignment assignment1 = new Assignment(1, "TP 1", "Premier TP", mockCourse, testDueDate);
        Assignment assignment2 = new Assignment(2, "TP 2", "Deuxième TP", mockCourse, testDueDate);

        assertEquals(mockCourse, assignment1.getCourseID(), "Les deux assignments doivent avoir le même cours");
        assertEquals(mockCourse, assignment2.getCourseID(), "Les deux assignments doivent avoir le même cours");
        assertNotEquals(assignment1.getAssignmentId(), assignment2.getAssignmentId(), "Les IDs doivent être différents");
        assertNotEquals(assignment1.getTitle(), assignment2.getTitle(), "Les titres doivent être différents");
    }

    @Test
    @DisplayName("Test comparaison de dates limites")
    void testCompareDueDates() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date date1 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date date2 = calendar.getTime();

        Assignment assignment1 = new Assignment(1, "TP 1", "Description", mockCourse, date1);
        Assignment assignment2 = new Assignment(2, "TP 2", "Description", mockCourse, date2);

        assertTrue(assignment1.getDueDate().before(assignment2.getDueDate()),
                "L'assignment 1 doit avoir une date limite plus tôt");
    }
}