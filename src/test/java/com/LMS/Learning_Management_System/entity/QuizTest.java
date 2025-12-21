package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Quiz Entity Tests")
class QuizTest {

    private Quiz quiz;
    private Course mockCourse;
    private Date testCreationDate;

    @BeforeEach
    void setUp() {
        // Création d'un mock course pour les tests
        mockCourse = new Course();
        mockCourse.setCourseId(1);
        mockCourse.setCourseName("Spring Boot Avancé");

        // Création d'une date de test
        testCreationDate = new Date();
    }

    @Test
    @DisplayName("Test création Quiz avec constructeur par défaut")
    void testDefaultConstructor() {
        quiz = new Quiz();

        assertNotNull(quiz, "Le quiz ne doit pas être null");
        assertEquals(0, quiz.getQuizId(), "L'ID par défaut doit être 0");
        assertNull(quiz.getTitle(), "Le titre par défaut doit être null");
        assertNull(quiz.getQuestionCount(), "Le nombre de questions par défaut doit être null");
        assertNull(quiz.getRandomized(), "Le randomized par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Quiz avec constructeur complet (avec ID)")
    void testFullConstructorWithId() {
        quiz = new Quiz(1, "Quiz Final", mockCourse, 10, true, testCreationDate);

        assertNotNull(quiz, "Le quiz ne doit pas être null");
        assertEquals(1, quiz.getQuizId(), "L'ID doit être 1");
        assertEquals("Quiz Final", quiz.getTitle(), "Le titre doit correspondre");
        assertEquals(mockCourse, quiz.getCourse(), "Le cours doit correspondre");
        assertEquals(10, quiz.getQuestionCount(), "Le nombre de questions doit être 10");
        assertTrue(quiz.getRandomized(), "Le quiz doit être randomisé");
        assertEquals(testCreationDate, quiz.getCreationDate(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test création Quiz avec constructeur sans ID")
    void testConstructorWithoutId() {
        quiz = new Quiz("Quiz Intermédiaire", mockCourse, 5, false, testCreationDate);

        assertNotNull(quiz, "Le quiz ne doit pas être null");
        assertEquals(0, quiz.getQuizId(), "L'ID par défaut doit être 0");
        assertEquals("Quiz Intermédiaire", quiz.getTitle(), "Le titre doit correspondre");
        assertEquals(mockCourse, quiz.getCourse(), "Le cours doit correspondre");
        assertEquals(5, quiz.getQuestionCount(), "Le nombre de questions doit être 5");
        assertFalse(quiz.getRandomized(), "Le quiz ne doit pas être randomisé");
    }

    @Test
    @DisplayName("Test setters et getters - QuizId")
    void testQuizIdSetterGetter() {
        quiz = new Quiz();
        quiz.setQuizId(100);

        assertEquals(100, quiz.getQuizId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - Title")
    void testTitleSetterGetter() {
        quiz = new Quiz();
        quiz.setTitle("Quiz de mi-parcours");

        assertEquals("Quiz de mi-parcours", quiz.getTitle(), "Le titre doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - Course")
    void testCourseSetterGetter() {
        quiz = new Quiz();
        quiz.setCourse(mockCourse);

        assertEquals(mockCourse, quiz.getCourse(), "Le cours doit correspondre");
        assertEquals(1, quiz.getCourse().getCourseId(), "L'ID du cours doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - QuestionCount")
    void testQuestionCountSetterGetter() {
        quiz = new Quiz();
        quiz.setQuestionCount(15);

        assertEquals(15, quiz.getQuestionCount(), "Le nombre de questions doit être 15");
    }

    @Test
    @DisplayName("Test setters et getters - Randomized true")
    void testRandomizedSetterGetterTrue() {
        quiz = new Quiz();
        quiz.setRandomized(true);

        assertTrue(quiz.getRandomized(), "Randomized doit être true");
    }

    @Test
    @DisplayName("Test setters et getters - Randomized false")
    void testRandomizedSetterGetterFalse() {
        quiz = new Quiz();
        quiz.setRandomized(false);

        assertFalse(quiz.getRandomized(), "Randomized doit être false");
    }

    @Test
    @DisplayName("Test setters et getters - CreationDate")
    void testCreationDateSetterGetter() {
        quiz = new Quiz();
        quiz.setCreationDate(testCreationDate);

        assertEquals(testCreationDate, quiz.getCreationDate(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test création quiz randomisé")
    void testCreateRandomizedQuiz() {
        quiz = new Quiz(1, "Quiz Aléatoire", mockCourse, 20, true, testCreationDate);

        assertTrue(quiz.getRandomized(), "Le quiz doit être randomisé");
        assertEquals(20, quiz.getQuestionCount(), "Le nombre de questions doit être 20");
    }

    @Test
    @DisplayName("Test création quiz non randomisé")
    void testCreateNonRandomizedQuiz() {
        quiz = new Quiz(1, "Quiz Séquentiel", mockCourse, 10, false, testCreationDate);

        assertFalse(quiz.getRandomized(), "Le quiz ne doit pas être randomisé");
    }

    @Test
    @DisplayName("Test modification du titre")
    void testModifyTitle() {
        quiz = new Quiz(1, "Titre initial", mockCourse, 10, true, testCreationDate);

        quiz.setTitle("Titre modifié");

        assertEquals("Titre modifié", quiz.getTitle(), "Le titre doit être modifié");
    }

    @Test
    @DisplayName("Test modification du nombre de questions")
    void testModifyQuestionCount() {
        quiz = new Quiz(1, "Quiz", mockCourse, 10, true, testCreationDate);

        quiz.setQuestionCount(25);

        assertEquals(25, quiz.getQuestionCount(), "Le nombre de questions doit être modifié");
    }

    @Test
    @DisplayName("Test modification du statut randomized")
    void testModifyRandomized() {
        quiz = new Quiz(1, "Quiz", mockCourse, 10, true, testCreationDate);

        quiz.setRandomized(false);

        assertFalse(quiz.getRandomized(), "Le statut randomized doit être modifié");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Course")
    void testManyToOneRelationWithCourse() {
        quiz = new Quiz();
        quiz.setCourse(mockCourse);

        assertNotNull(quiz.getCourse(), "Le cours ne doit pas être null");
        assertEquals(mockCourse.getCourseId(), quiz.getCourse().getCourseId(), "L'ID du cours doit correspondre");
        assertEquals("Spring Boot Avancé", quiz.getCourse().getCourseName(), "Le nom du cours doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        quiz = new Quiz(1, "Quiz Test", mockCourse, 10, true, testCreationDate);

        String result = quiz.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("quizId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("title='Quiz Test'"), "toString doit contenir le titre");
        assertTrue(result.contains("questionCount=10"), "toString doit contenir le nombre de questions");
        assertTrue(result.contains("randomized=true"), "toString doit contenir randomized");
        assertTrue(result.contains("Quiz{"), "toString doit commencer par Quiz{");
    }

    @Test
    @DisplayName("Test Quiz avec titre vide")
    void testQuizWithEmptyTitle() {
        quiz = new Quiz(1, "", mockCourse, 10, true, testCreationDate);

        assertEquals("", quiz.getTitle(), "Le titre vide doit être accepté");
    }

    @Test
    @DisplayName("Test Quiz avec titre null")
    void testQuizWithNullTitle() {
        quiz = new Quiz(1, null, mockCourse, 10, true, testCreationDate);

        assertNull(quiz.getTitle(), "Le titre null doit être accepté");
    }

    @Test
    @DisplayName("Test Quiz avec questionCount null")
    void testQuizWithNullQuestionCount() {
        quiz = new Quiz(1, "Quiz", mockCourse, null, true, testCreationDate);

        assertNull(quiz.getQuestionCount(), "Le questionCount null doit être accepté");
    }

    @Test
    @DisplayName("Test Quiz avec questionCount à zéro")
    void testQuizWithZeroQuestionCount() {
        quiz = new Quiz(1, "Quiz", mockCourse, 0, true, testCreationDate);

        assertEquals(0, quiz.getQuestionCount(), "Le questionCount à zéro doit être accepté");
    }

    @Test
    @DisplayName("Test Quiz avec questionCount négatif")
    void testQuizWithNegativeQuestionCount() {
        quiz = new Quiz(1, "Quiz", mockCourse, -5, true, testCreationDate);

        assertEquals(-5, quiz.getQuestionCount(), "Le questionCount négatif doit être accepté");
    }

    @Test
    @DisplayName("Test Quiz avec questionCount très élevé")
    void testQuizWithHighQuestionCount() {
        quiz = new Quiz(1, "Quiz", mockCourse, 1000, true, testCreationDate);

        assertEquals(1000, quiz.getQuestionCount(), "Le questionCount élevé doit être accepté");
    }

    @Test
    @DisplayName("Test Quiz avec randomized null")
    void testQuizWithNullRandomized() {
        quiz = new Quiz(1, "Quiz", mockCourse, 10, null, testCreationDate);

        assertNull(quiz.getRandomized(), "Le randomized null doit être accepté");
    }

    @Test
    @DisplayName("Test modification du cours associé")
    void testModifyAssociatedCourse() {
        quiz = new Quiz(1, "Quiz", mockCourse, 10, true, testCreationDate);

        Course newCourse = new Course();
        newCourse.setCourseId(2);
        newCourse.setCourseName("Nouveau cours");

        quiz.setCourse(newCourse);

        assertEquals(newCourse, quiz.getCourse(), "Le cours doit être modifié");
        assertEquals(2, quiz.getCourse().getCourseId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test Quiz avec date de création dans le passé")
    void testQuizWithPastCreationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date pastDate = calendar.getTime();

        quiz = new Quiz(1, "Quiz", mockCourse, 10, true, pastDate);

        assertEquals(pastDate, quiz.getCreationDate(), "La date passée doit être acceptée");
        assertTrue(quiz.getCreationDate().before(new Date()), "La date doit être dans le passé");
    }

    @Test
    @DisplayName("Test Quiz avec date de création future")
    void testQuizWithFutureCreationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date futureDate = calendar.getTime();

        quiz = new Quiz(1, "Quiz", mockCourse, 10, true, futureDate);

        assertEquals(futureDate, quiz.getCreationDate(), "La date future doit être acceptée");
    }

    @Test
    @DisplayName("Test Quiz avec titre contenant des caractères spéciaux")
    void testQuizWithSpecialCharactersInTitle() {
        String specialTitle = "Quiz #1: Spring Boot & JPA (évaluation)";
        quiz = new Quiz(1, specialTitle, mockCourse, 10, true, testCreationDate);

        assertEquals(specialTitle, quiz.getTitle(), "Les caractères spéciaux doivent être acceptés");
    }

    @Test
    @DisplayName("Test Quiz avec titre long")
    void testQuizWithLongTitle() {
        String longTitle = "Quiz complet sur Spring Boot avec tests unitaires et intégration continue";
        quiz = new Quiz(1, longTitle, mockCourse, 10, true, testCreationDate);

        assertEquals(longTitle, quiz.getTitle(), "Le titre long doit être accepté");
        assertTrue(quiz.getTitle().length() > 50, "Le titre doit être long");
    }

    @Test
    @DisplayName("Test plusieurs quiz pour le même cours")
    void testMultipleQuizzesForSameCourse() {
        Quiz quiz1 = new Quiz(1, "Quiz 1", mockCourse, 10, true, testCreationDate);
        Quiz quiz2 = new Quiz(2, "Quiz 2", mockCourse, 15, false, testCreationDate);

        assertEquals(mockCourse, quiz1.getCourse(), "Les deux quiz doivent avoir le même cours");
        assertEquals(mockCourse, quiz2.getCourse(), "Les deux quiz doivent avoir le même cours");
        assertNotEquals(quiz1.getQuizId(), quiz2.getQuizId(), "Les IDs doivent être différents");
        assertNotEquals(quiz1.getTitle(), quiz2.getTitle(), "Les titres doivent être différents");
    }

    @Test
    @DisplayName("Test modification de la date de création")
    void testModifyCreationDate() {
        quiz = new Quiz(1, "Quiz", mockCourse, 10, true, testCreationDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        Date newDate = calendar.getTime();

        quiz.setCreationDate(newDate);

        assertEquals(newDate, quiz.getCreationDate(), "La date doit être modifiée");
        assertNotEquals(testCreationDate, quiz.getCreationDate(), "La nouvelle date doit être différente");
    }

    @Test
    @DisplayName("Test Quiz avec creationDate null")
    void testQuizWithNullCreationDate() {
        quiz = new Quiz(1, "Quiz", mockCourse, 10, true, null);

        assertNull(quiz.getCreationDate(), "La date null doit être acceptée");
    }

    @Test
    @DisplayName("Test comparaison de deux quiz")
    void testCompareQuizzes() {
        Quiz quiz1 = new Quiz(1, "Quiz A", mockCourse, 10, true, testCreationDate);
        Quiz quiz2 = new Quiz(2, "Quiz B", mockCourse, 20, false, testCreationDate);

        assertNotEquals(quiz1.getQuizId(), quiz2.getQuizId(), "Les IDs doivent être différents");
        assertNotEquals(quiz1.getQuestionCount(), quiz2.getQuestionCount(), "Les nombres de questions doivent être différents");
        assertNotEquals(quiz1.getRandomized(), quiz2.getRandomized(), "Les statuts randomized doivent être différents");
    }
}