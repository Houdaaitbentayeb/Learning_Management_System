package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Course Entity Tests")
class CourseTest {

    private Course course;
    private Instructor mockInstructor;
    private Date testCreationDate;
    private List<Lesson> mockLessons;

    @BeforeEach
    void setUp() {
        // Création d'un mock instructor pour les tests
        mockInstructor = new Instructor();
        mockInstructor.setUserAccountId(1);

        // Création d'une date de test
        testCreationDate = new Date();

        // Création d'une liste de lessons mock
        mockLessons = new ArrayList<>();
        Lesson lesson1 = new Lesson();
        lesson1.setLessonId(1);
        lesson1.setLessonName("Introduction");

        Lesson lesson2 = new Lesson();
        lesson2.setLessonId(2);
        lesson2.setLessonName("Concepts avancés");

        mockLessons.add(lesson1);
        mockLessons.add(lesson2);
    }

    @Test
    @DisplayName("Test création Course avec constructeur par défaut")
    void testDefaultConstructor() {
        course = new Course();

        assertNotNull(course, "Le cours ne doit pas être null");
        assertEquals(0, course.getCourseId(), "L'ID par défaut doit être 0");
        assertNull(course.getCourseName(), "Le nom par défaut doit être null");
        assertNull(course.getDescription(), "La description par défaut doit être null");
        assertEquals(0, course.getDuration(), "La durée par défaut doit être 0");
    }

    @Test
    @DisplayName("Test création Course avec constructeur complet")
    void testFullConstructor() {
        course = new Course(
                1,
                "Spring Boot Masterclass",
                mockInstructor,
                "Cours complet sur Spring Boot",
                "spring-boot-intro.mp4",
                40,
                testCreationDate
        );

        assertNotNull(course, "Le cours ne doit pas être null");
        assertEquals(1, course.getCourseId(), "L'ID doit être 1");
        assertEquals("Spring Boot Masterclass", course.getCourseName(), "Le nom doit correspondre");
        assertEquals(mockInstructor, course.getInstructorId(), "L'instructeur doit correspondre");
        assertEquals("Cours complet sur Spring Boot", course.getDescription(), "La description doit correspondre");
        assertEquals("spring-boot-intro.mp4", course.getMedia(), "Le média doit correspondre");
        assertEquals(40, course.getDuration(), "La durée doit être 40");
        assertEquals(testCreationDate, course.getCreationDate(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - CourseId")
    void testCourseIdSetterGetter() {
        course = new Course();
        course.setCourseId(100);

        assertEquals(100, course.getCourseId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - CourseName")
    void testCourseNameSetterGetter() {
        course = new Course();
        course.setCourseName("Java Avancé");

        assertEquals("Java Avancé", course.getCourseName(), "Le nom doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - InstructorId")
    void testInstructorIdSetterGetter() {
        course = new Course();
        course.setInstructorId(mockInstructor);

        assertEquals(mockInstructor, course.getInstructorId(), "L'instructeur doit correspondre");
        assertEquals(1, course.getInstructorId().getUserAccountId(), "L'ID de l'instructeur doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - Description")
    void testDescriptionSetterGetter() {
        course = new Course();
        String description = "Apprenez à maîtriser Spring Boot";
        course.setDescription(description);

        assertEquals(description, course.getDescription(), "La description doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - Media")
    void testMediaSetterGetter() {
        course = new Course();
        course.setMedia("video-course.mp4");

        assertEquals("video-course.mp4", course.getMedia(), "Le média doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - Duration")
    void testDurationSetterGetter() {
        course = new Course();
        course.setDuration(60);

        assertEquals(60, course.getDuration(), "La durée doit être 60");
    }

    @Test
    @DisplayName("Test setters et getters - CreationDate")
    void testCreationDateSetterGetter() {
        course = new Course();
        course.setCreationDate(testCreationDate);

        assertEquals(testCreationDate, course.getCreationDate(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - Lessons")
    void testLessonsSetterGetter() {
        course = new Course();
        course.setLessons(mockLessons);

        assertEquals(mockLessons, course.getLessons(), "La liste de lessons doit correspondre");
        assertEquals(2, course.getLessons().size(), "Il doit y avoir 2 lessons");
    }

    @Test
    @DisplayName("Test modification du nom du cours")
    void testModifyCourseName() {
        course = new Course(1, "Nom initial", mockInstructor, "Description", null, 30, testCreationDate);

        course.setCourseName("Nom modifié");

        assertEquals("Nom modifié", course.getCourseName(), "Le nom doit être modifié");
    }

    @Test
    @DisplayName("Test modification de la description")
    void testModifyDescription() {
        course = new Course(1, "Cours", mockInstructor, "Description initiale", null, 30, testCreationDate);

        course.setDescription("Nouvelle description détaillée");

        assertEquals("Nouvelle description détaillée", course.getDescription(), "La description doit être modifiée");
    }

    @Test
    @DisplayName("Test modification de la durée")
    void testModifyDuration() {
        course = new Course(1, "Cours", mockInstructor, "Description", null, 30, testCreationDate);

        course.setDuration(50);

        assertEquals(50, course.getDuration(), "La durée doit être modifiée à 50");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Instructor")
    void testManyToOneRelationWithInstructor() {
        course = new Course();
        course.setInstructorId(mockInstructor);

        assertNotNull(course.getInstructorId(), "L'instructeur ne doit pas être null");
        assertEquals(mockInstructor.getUserAccountId(), course.getInstructorId().getUserAccountId(),
                "L'ID de l'instructeur doit correspondre");
    }

    @Test
    @DisplayName("Test relation OneToMany avec Lessons")
    void testOneToManyRelationWithLessons() {
        course = new Course();
        course.setLessons(mockLessons);

        assertNotNull(course.getLessons(), "La liste de lessons ne doit pas être null");
        assertEquals(2, course.getLessons().size(), "Il doit y avoir 2 lessons");
        assertEquals("Introduction", course.getLessons().get(0).getLessonName(),
                "Le premier lesson doit être 'Introduction'");
    }

    @Test
    @DisplayName("Test ajout d'un lesson à la liste")
    void testAddLessonToList() {
        course = new Course();
        course.setLessons(new ArrayList<>());

        Lesson newLesson = new Lesson();
        newLesson.setLessonId(3);
        newLesson.setLessonName("Conclusion");

        course.getLessons().add(newLesson);

        assertEquals(1, course.getLessons().size(), "Il doit y avoir 1 lesson");
        assertEquals("Conclusion", course.getLessons().get(0).getLessonName(), "Le lesson doit être 'Conclusion'");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        course = new Course(1, "Spring Boot", mockInstructor, "Description test", "video.mp4", 40, testCreationDate);

        String result = course.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("courseId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("courseName='Spring Boot'"), "toString doit contenir le nom");
        assertTrue(result.contains("description='Description test'"), "toString doit contenir la description");
        assertTrue(result.contains("duration=40"), "toString doit contenir la durée");
        assertTrue(result.contains("Course{"), "toString doit commencer par Course{");
    }

    @Test
    @DisplayName("Test Course avec nom vide")
    void testCourseWithEmptyName() {
        course = new Course(1, "", mockInstructor, "Description", null, 30, testCreationDate);

        assertEquals("", course.getCourseName(), "Le nom vide doit être accepté");
    }

    @Test
    @DisplayName("Test Course avec description vide")
    void testCourseWithEmptyDescription() {
        course = new Course(1, "Cours", mockInstructor, "", null, 30, testCreationDate);

        assertEquals("", course.getDescription(), "La description vide doit être acceptée");
    }

    @Test
    @DisplayName("Test Course avec media null")
    void testCourseWithNullMedia() {
        course = new Course(1, "Cours", mockInstructor, "Description", null, 30, testCreationDate);

        assertNull(course.getMedia(), "Le média null doit être accepté");
    }

    @Test
    @DisplayName("Test Course avec durée zéro")
    void testCourseWithZeroDuration() {
        course = new Course(1, "Cours", mockInstructor, "Description", null, 0, testCreationDate);

        assertEquals(0, course.getDuration(), "La durée zéro doit être acceptée");
    }

    @Test
    @DisplayName("Test Course avec durée négative")
    void testCourseWithNegativeDuration() {
        course = new Course(1, "Cours", mockInstructor, "Description", null, -10, testCreationDate);

        assertEquals(-10, course.getDuration(), "La durée négative doit être acceptée");
    }

    @Test
    @DisplayName("Test Course avec durée très élevée")
    void testCourseWithHighDuration() {
        course = new Course(1, "Cours", mockInstructor, "Description", null, 500, testCreationDate);

        assertEquals(500, course.getDuration(), "La durée élevée doit être acceptée");
    }

    @Test
    @DisplayName("Test modification de l'instructeur associé")
    void testModifyAssociatedInstructor() {
        course = new Course(1, "Cours", mockInstructor, "Description", null, 30, testCreationDate);

        Instructor newInstructor = new Instructor();
        newInstructor.setUserAccountId(2);

        course.setInstructorId(newInstructor);

        assertEquals(newInstructor, course.getInstructorId(), "L'instructeur doit être modifié");
        assertEquals(2, course.getInstructorId().getUserAccountId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test Course avec date de création dans le passé")
    void testCourseWithPastCreationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        Date pastDate = calendar.getTime();

        course = new Course(1, "Cours", mockInstructor, "Description", null, 30, pastDate);

        assertEquals(pastDate, course.getCreationDate(), "La date passée doit être acceptée");
        assertTrue(course.getCreationDate().before(new Date()), "La date doit être dans le passé");
    }

    @Test
    @DisplayName("Test Course avec nom contenant des caractères spéciaux")
    void testCourseWithSpecialCharactersInName() {
        String specialName = "Java & Spring: Les bases (niveau 1)";
        course = new Course(1, specialName, mockInstructor, "Description", null, 30, testCreationDate);

        assertEquals(specialName, course.getCourseName(), "Les caractères spéciaux doivent être acceptés");
    }

    @Test
    @DisplayName("Test Course avec description longue")
    void testCourseWithLongDescription() {
        String longDescription = "Ceci est une très longue description qui contient de nombreux détails " +
                "sur le cours, les objectifs d'apprentissage, les prérequis nécessaires, " +
                "et toutes les informations importantes pour les étudiants potentiels. " +
                "Le cours couvre de nombreux sujets avancés et techniques.";

        course = new Course(1, "Cours", mockInstructor, longDescription, null, 30, testCreationDate);

        assertEquals(longDescription, course.getDescription(), "La description longue doit être acceptée");
        assertTrue(course.getDescription().length() > 100, "La description doit être longue");
    }

    @Test
    @DisplayName("Test Course avec différents types de média")
    void testCourseWithDifferentMediaTypes() {
        // Test avec vidéo
        course = new Course(1, "Cours", mockInstructor, "Description", "course.mp4", 30, testCreationDate);
        assertEquals("course.mp4", course.getMedia(), "Le média vidéo doit être accepté");

        // Test avec PDF
        course.setMedia("course.pdf");
        assertEquals("course.pdf", course.getMedia(), "Le média PDF doit être accepté");

        // Test avec image
        course.setMedia("course-thumb.jpg");
        assertEquals("course-thumb.jpg", course.getMedia(), "Le média image doit être accepté");
    }

    @Test
    @DisplayName("Test plusieurs cours pour le même instructeur")
    void testMultipleCoursesForSameInstructor() {
        Course course1 = new Course(1, "Cours 1", mockInstructor, "Description 1", null, 30, testCreationDate);
        Course course2 = new Course(2, "Cours 2", mockInstructor, "Description 2", null, 40, testCreationDate);

        assertEquals(mockInstructor, course1.getInstructorId(), "Les deux cours doivent avoir le même instructeur");
        assertEquals(mockInstructor, course2.getInstructorId(), "Les deux cours doivent avoir le même instructeur");
        assertNotEquals(course1.getCourseId(), course2.getCourseId(), "Les IDs doivent être différents");
    }

    @Test
    @DisplayName("Test Course avec liste de lessons vide")
    void testCourseWithEmptyLessonsList() {
        course = new Course();
        course.setLessons(new ArrayList<>());

        assertNotNull(course.getLessons(), "La liste ne doit pas être null");
        assertEquals(0, course.getLessons().size(), "La liste doit être vide");
        assertTrue(course.getLessons().isEmpty(), "La liste doit être vide");
    }

    @Test
    @DisplayName("Test Course avec liste de lessons null")
    void testCourseWithNullLessonsList() {
        course = new Course();
        course.setLessons(null);

        assertNull(course.getLessons(), "La liste null doit être acceptée");
    }

    @Test
    @DisplayName("Test modification du média")
    void testModifyMedia() {
        course = new Course(1, "Cours", mockInstructor, "Description", "old-video.mp4", 30, testCreationDate);

        course.setMedia("new-video.mp4");

        assertEquals("new-video.mp4", course.getMedia(), "Le média doit être modifié");
    }

    @Test
    @DisplayName("Test Course avec media de longueur maximale (64 caractères)")
    void testCourseWithMaxLengthMedia() {
        String maxLengthMedia = "a".repeat(64);
        course = new Course(1, "Cours", mockInstructor, "Description", maxLengthMedia, 30, testCreationDate);

        assertEquals(maxLengthMedia, course.getMedia(), "Le média de longueur max doit être accepté");
        assertEquals(64, course.getMedia().length(), "La longueur doit être 64");
    }
}