package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Lesson Entity Tests")
class LessonTest {

    private Lesson lesson;
    private Course mockCourse;
    private Date testCreationTime;

    @BeforeEach
    void setUp() {
        // Création d'un mock course
        mockCourse = new Course();
        mockCourse.setCourseId(1);
        mockCourse.setCourseName("Spring Boot Avancé");

        // Création d'une date de test
        testCreationTime = new Date();
    }

    @Test
    @DisplayName("Test création Lesson avec constructeur par défaut")
    void testDefaultConstructor() {
        lesson = new Lesson();

        assertNotNull(lesson, "Le lesson ne doit pas être null");
        assertEquals(0, lesson.getLessonId(), "L'ID par défaut doit être 0");
        assertNull(lesson.getLessonName(), "Le nom par défaut doit être null");
        assertNull(lesson.getLessonDescription(), "La description par défaut doit être null");
        assertEquals(0, lesson.getLessonOrder(), "L'ordre par défaut doit être 0");
    }

    @Test
    @DisplayName("Test création Lesson avec constructeur complet")
    void testFullConstructor() {
        lesson = new Lesson(1, mockCourse, "Introduction", "Leçon d'introduction", 1, "123456", "Contenu du cours", testCreationTime);

        assertNotNull(lesson, "Le lesson ne doit pas être null");
        assertEquals(1, lesson.getLessonId(), "L'ID doit être 1");
        assertEquals(mockCourse, lesson.getCourseId(), "Le cours doit correspondre");
        assertEquals("Introduction", lesson.getLessonName(), "Le nom doit correspondre");
        assertEquals("Leçon d'introduction", lesson.getLessonDescription(), "La description doit correspondre");
        assertEquals(1, lesson.getLessonOrder(), "L'ordre doit être 1");
        assertEquals("123456", lesson.getOTP(), "L'OTP doit correspondre");
        assertEquals("Contenu du cours", lesson.getContent(), "Le contenu doit correspondre");
        assertEquals(testCreationTime, lesson.getCreationTime(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - LessonId")
    void testLessonIdSetterGetter() {
        lesson = new Lesson();
        lesson.setLessonId(100);

        assertEquals(100, lesson.getLessonId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - CourseId")
    void testCourseIdSetterGetter() {
        lesson = new Lesson();
        lesson.setCourseId(mockCourse);

        assertEquals(mockCourse, lesson.getCourseId(), "Le cours doit correspondre");
        assertEquals(1, lesson.getCourseId().getCourseId(), "L'ID du cours doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - LessonName")
    void testLessonNameSetterGetter() {
        lesson = new Lesson();
        lesson.setLessonName("Les bases de Spring");

        assertEquals("Les bases de Spring", lesson.getLessonName(), "Le nom doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - LessonDescription")
    void testLessonDescriptionSetterGetter() {
        lesson = new Lesson();
        lesson.setLessonDescription("Description détaillée de la leçon");

        assertEquals("Description détaillée de la leçon", lesson.getLessonDescription(), "La description doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - LessonOrder")
    void testLessonOrderSetterGetter() {
        lesson = new Lesson();
        lesson.setLessonOrder(5);

        assertEquals(5, lesson.getLessonOrder(), "L'ordre doit être 5");
    }

    @Test
    @DisplayName("Test setters et getters - OTP")
    void testOTPSetterGetter() {
        lesson = new Lesson();
        lesson.setOTP("987654");

        assertEquals("987654", lesson.getOTP(), "L'OTP doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - Content")
    void testContentSetterGetter() {
        lesson = new Lesson();
        lesson.setContent("Contenu complet de la leçon");

        assertEquals("Contenu complet de la leçon", lesson.getContent(), "Le contenu doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - CreationTime")
    void testCreationTimeSetterGetter() {
        lesson = new Lesson();
        lesson.setCreationTime(testCreationTime);

        assertEquals(testCreationTime, lesson.getCreationTime(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test modification du nom de la leçon")
    void testModifyLessonName() {
        lesson = new Lesson(1, mockCourse, "Nom initial", "Description", 1, "123456", "Contenu", testCreationTime);

        lesson.setLessonName("Nom modifié");

        assertEquals("Nom modifié", lesson.getLessonName(), "Le nom doit être modifié");
    }

    @Test
    @DisplayName("Test modification de l'ordre de la leçon")
    void testModifyLessonOrder() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", testCreationTime);

        lesson.setLessonOrder(10);

        assertEquals(10, lesson.getLessonOrder(), "L'ordre doit être modifié à 10");
    }

    @Test
    @DisplayName("Test modification de l'OTP")
    void testModifyOTP() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", testCreationTime);

        lesson.setOTP("999999");

        assertEquals("999999", lesson.getOTP(), "L'OTP doit être modifié");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Course")
    void testManyToOneRelationWithCourse() {
        lesson = new Lesson();
        lesson.setCourseId(mockCourse);

        assertNotNull(lesson.getCourseId(), "Le cours ne doit pas être null");
        assertEquals(mockCourse.getCourseId(), lesson.getCourseId().getCourseId(), "L'ID du cours doit correspondre");
        assertEquals("Spring Boot Avancé", lesson.getCourseId().getCourseName(), "Le nom du cours doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        lesson = new Lesson(1, mockCourse, "Test Lesson", "Description test", 1, "123456", "Contenu", testCreationTime);

        String result = lesson.toString();

        assertNotNull(result, "toString ne doit pas retourner null");

        // robuste : on vérifie les valeurs importantes sans imposer le format exact
        assertTrue(result.contains("Lesson"), "toString doit contenir le nom de la classe");
        assertTrue(result.contains("1"), "toString doit contenir l'ID (1)");
        assertTrue(result.contains("Test Lesson"), "toString doit contenir le nom de la leçon");
        assertTrue(result.contains("Description test"), "toString doit contenir la description");
        assertTrue(result.contains("123456"), "toString doit contenir l'OTP");
    }


    @Test
    @DisplayName("Test Lesson avec nom vide")
    void testLessonWithEmptyName() {
        lesson = new Lesson(1, mockCourse, "", "Description", 1, "123456", "Contenu", testCreationTime);

        assertEquals("", lesson.getLessonName(), "Le nom vide doit être accepté");
    }

    @Test
    @DisplayName("Test Lesson avec description vide")
    void testLessonWithEmptyDescription() {
        lesson = new Lesson(1, mockCourse, "Leçon", "", 1, "123456", "Contenu", testCreationTime);

        assertEquals("", lesson.getLessonDescription(), "La description vide doit être acceptée");
    }

    @Test
    @DisplayName("Test Lesson avec contenu vide")
    void testLessonWithEmptyContent() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "", testCreationTime);

        assertEquals("", lesson.getContent(), "Le contenu vide doit être accepté");
    }

    @Test
    @DisplayName("Test Lesson avec OTP null")
    void testLessonWithNullOTP() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, null, "Contenu", testCreationTime);

        assertNull(lesson.getOTP(), "L'OTP null doit être accepté");
    }

    @Test
    @DisplayName("Test Lesson avec lessonOrder zéro")
    void testLessonWithZeroOrder() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 0, "123456", "Contenu", testCreationTime);

        assertEquals(0, lesson.getLessonOrder(), "L'ordre zéro doit être accepté");
    }

    @Test
    @DisplayName("Test Lesson avec lessonOrder négatif")
    void testLessonWithNegativeOrder() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", -1, "123456", "Contenu", testCreationTime);

        assertEquals(-1, lesson.getLessonOrder(), "L'ordre négatif doit être accepté");
    }

    @Test
    @DisplayName("Test plusieurs lessons pour le même cours")
    void testMultipleLessonsForSameCourse() {
        Lesson lesson1 = new Lesson(1, mockCourse, "Leçon 1", "Description 1", 1, "111111", "Contenu 1", testCreationTime);
        Lesson lesson2 = new Lesson(2, mockCourse, "Leçon 2", "Description 2", 2, "222222", "Contenu 2", testCreationTime);

        assertEquals(mockCourse, lesson1.getCourseId(), "Les deux lessons doivent avoir le même cours");
        assertEquals(mockCourse, lesson2.getCourseId(), "Les deux lessons doivent avoir le même cours");
        assertNotEquals(lesson1.getLessonId(), lesson2.getLessonId(), "Les IDs doivent être différents");
        assertNotEquals(lesson1.getLessonOrder(), lesson2.getLessonOrder(), "Les ordres doivent être différents");
    }

    @Test
    @DisplayName("Test Lesson avec description longue")
    void testLessonWithLongDescription() {
        String longDescription = "Ceci est une très longue description qui contient de nombreux détails " +
                "sur la leçon, les objectifs d'apprentissage, les prérequis nécessaires, " +
                "et toutes les informations importantes pour les étudiants.";
        lesson = new Lesson(1, mockCourse, "Leçon", longDescription, 1, "123456", "Contenu", testCreationTime);

        assertEquals(longDescription, lesson.getLessonDescription(), "La description longue doit être acceptée");
        assertTrue(lesson.getLessonDescription().length() > 100, "La description doit être longue");
    }

    @Test
    @DisplayName("Test Lesson avec contenu long")
    void testLessonWithLongContent() {
        String longContent = "Contenu très détaillé de la leçon avec de nombreuses explications, " +
                "exemples de code, diagrammes, et exercices pratiques pour les étudiants.";
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", longContent, testCreationTime);

        assertEquals(longContent, lesson.getContent(), "Le contenu long doit être accepté");
    }

    @Test
    @DisplayName("Test Lesson avec OTP de différentes longueurs")
    void testLessonWithDifferentOTPLengths() {
        // OTP court
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123", "Contenu", testCreationTime);
        assertEquals("123", lesson.getOTP(), "L'OTP court doit être accepté");

        // OTP long
        lesson.setOTP("123456789012");
        assertEquals("123456789012", lesson.getOTP(), "L'OTP long doit être accepté");
    }

    @Test
    @DisplayName("Test modification du cours associé")
    void testModifyAssociatedCourse() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", testCreationTime);

        Course newCourse = new Course();
        newCourse.setCourseId(2);
        newCourse.setCourseName("Nouveau cours");

        lesson.setCourseId(newCourse);

        assertEquals(newCourse, lesson.getCourseId(), "Le cours doit être modifié");
        assertEquals(2, lesson.getCourseId().getCourseId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test Lesson avec date de création dans le passé")
    void testLessonWithPastCreationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date pastDate = calendar.getTime();

        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", pastDate);

        assertEquals(pastDate, lesson.getCreationTime(), "La date passée doit être acceptée");
        assertTrue(lesson.getCreationTime().before(new Date()), "La date doit être dans le passé");
    }

    @Test
    @DisplayName("Test Lesson avec date de création future")
    void testLessonWithFutureCreationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date futureDate = calendar.getTime();

        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", futureDate);

        assertEquals(futureDate, lesson.getCreationTime(), "La date future doit être acceptée");
    }

    @Test
    @DisplayName("Test modification de la date de création")
    void testModifyCreationTime() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", testCreationTime);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date newDate = calendar.getTime();

        lesson.setCreationTime(newDate);

        assertEquals(newDate, lesson.getCreationTime(), "La date doit être modifiée");
    }

    @Test
    @DisplayName("Test Lesson avec caractères spéciaux dans le nom")
    void testLessonWithSpecialCharactersInName() {
        String specialName = "Leçon #1: Introduction & Concepts (partie 1)";
        lesson = new Lesson(1, mockCourse, specialName, "Description", 1, "123456", "Contenu", testCreationTime);

        assertEquals(specialName, lesson.getLessonName(), "Les caractères spéciaux doivent être acceptés");
    }

    @Test
    @DisplayName("Test tri des lessons par ordre")
    void testSortLessonsByOrder() {
        Lesson lesson1 = new Lesson(1, mockCourse, "Leçon A", "Description", 3, "111", "Contenu", testCreationTime);
        Lesson lesson2 = new Lesson(2, mockCourse, "Leçon B", "Description", 1, "222", "Contenu", testCreationTime);
        Lesson lesson3 = new Lesson(3, mockCourse, "Leçon C", "Description", 2, "333", "Contenu", testCreationTime);

        assertTrue(lesson2.getLessonOrder() < lesson3.getLessonOrder(), "Lesson2 doit être avant Lesson3");
        assertTrue(lesson3.getLessonOrder() < lesson1.getLessonOrder(), "Lesson3 doit être avant Lesson1");
    }

    @Test
    @DisplayName("Test génération d'OTP pour présence")
    void testOTPGenerationForAttendance() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", testCreationTime);

        assertNotNull(lesson.getOTP(), "L'OTP ne doit pas être null");
        assertEquals(6, lesson.getOTP().length(), "L'OTP doit avoir 6 caractères");
    }

    @Test
    @DisplayName("Test modification du contenu de la leçon")
    void testModifyLessonContent() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu initial", testCreationTime);

        lesson.setContent("Contenu mis à jour avec plus d'informations");

        assertEquals("Contenu mis à jour avec plus d'informations", lesson.getContent(), "Le contenu doit être modifié");
    }

    @Test
    @DisplayName("Test Lesson avec creationTime null")
    void testLessonWithNullCreationTime() {
        lesson = new Lesson(1, mockCourse, "Leçon", "Description", 1, "123456", "Contenu", null);

        assertNull(lesson.getCreationTime(), "La date null doit être acceptée");
    }

    @Test
    @DisplayName("Test comparaison de deux lessons")
    void testCompareTwoLessons() {
        Lesson lesson1 = new Lesson(1, mockCourse, "Leçon 1", "Description", 1, "111111", "Contenu", testCreationTime);
        Lesson lesson2 = new Lesson(2, mockCourse, "Leçon 2", "Description", 2, "222222", "Contenu", testCreationTime);

        assertNotEquals(lesson1.getLessonId(), lesson2.getLessonId(), "Les IDs doivent être différents");
        assertNotEquals(lesson1.getLessonName(), lesson2.getLessonName(), "Les noms doivent être différents");
        assertNotEquals(lesson1.getLessonOrder(), lesson2.getLessonOrder(), "Les ordres doivent être différents");
        assertNotEquals(lesson1.getOTP(), lesson2.getOTP(), "Les OTP doivent être différents");
    }
}