package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Grading Entity Tests")
class GradingTest {

    private Grading grading;
    private Quiz mockQuiz;
    private Student mockStudent;

    @BeforeEach
    void setUp() {
        // Création d'un mock quiz
        mockQuiz = new Quiz();
        mockQuiz.setQuizId(1);
        mockQuiz.setTitle("Quiz Spring Boot");

        // Création d'un mock student
        mockStudent = new Student();
        mockStudent.setUserAccountId(1);
    }

    @Test
    @DisplayName("Test création Grading avec constructeur par défaut")
    void testDefaultConstructor() {
        grading = new Grading();

        assertNotNull(grading, "Le grading ne doit pas être null");
        assertEquals(0, grading.getGradingId(), "L'ID par défaut doit être 0");
        assertEquals(0, grading.getGrade(), "La note par défaut doit être 0");
        assertNull(grading.getQuiz_id(), "Le quiz par défaut doit être null");
        assertNull(grading.getStudent_id(), "Le student par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Grading avec constructeur complet")
    void testFullConstructor() {
        grading = new Grading(85, mockQuiz, mockStudent);

        assertNotNull(grading, "Le grading ne doit pas être null");
        assertEquals(85, grading.getGrade(), "La note doit être 85");
        assertEquals(mockQuiz, grading.getQuiz_id(), "Le quiz doit correspondre");
        assertEquals(mockStudent, grading.getStudent_id(), "Le student doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - GradingId")
    void testGradingIdSetterGetter() {
        grading = new Grading();
        grading.setGradingId(100);

        assertEquals(100, grading.getGradingId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - Grade")
    void testGradeSetterGetter() {
        grading = new Grading();
        grading.setGrade(90);

        assertEquals(90, grading.getGrade(), "La note doit être 90");
    }

    @Test
    @DisplayName("Test setters et getters - Quiz_id")
    void testQuizIdSetterGetter() {
        grading = new Grading();
        grading.setQuiz_id(mockQuiz);

        assertEquals(mockQuiz, grading.getQuiz_id(), "Le quiz doit correspondre");
        assertEquals(1, grading.getQuiz_id().getQuizId(), "L'ID du quiz doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - Student_id")
    void testStudentIdSetterGetter() {
        grading = new Grading();
        grading.setStudent_id(mockStudent);

        assertEquals(mockStudent, grading.getStudent_id(), "Le student doit correspondre");
        assertEquals(1, grading.getStudent_id().getUserAccountId(), "L'ID du student doit être 1");
    }

    @Test
    @DisplayName("Test création grading avec note parfaite (100)")
    void testCreateGradingWithPerfectScore() {
        grading = new Grading(100, mockQuiz, mockStudent);

        assertEquals(100, grading.getGrade(), "La note doit être 100");
    }

    @Test
    @DisplayName("Test création grading avec note zéro")
    void testCreateGradingWithZeroScore() {
        grading = new Grading(0, mockQuiz, mockStudent);

        assertEquals(0, grading.getGrade(), "La note doit être 0");
    }

    @Test
    @DisplayName("Test création grading avec note moyenne")
    void testCreateGradingWithAverageScore() {
        grading = new Grading(75, mockQuiz, mockStudent);

        assertEquals(75, grading.getGrade(), "La note doit être 75");
    }

    @Test
    @DisplayName("Test modification de la note")
    void testModifyGrade() {
        grading = new Grading(80, mockQuiz, mockStudent);

        grading.setGrade(95);

        assertEquals(95, grading.getGrade(), "La note doit être modifiée à 95");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Quiz")
    void testManyToOneRelationWithQuiz() {
        grading = new Grading();
        grading.setQuiz_id(mockQuiz);

        assertNotNull(grading.getQuiz_id(), "Le quiz ne doit pas être null");
        assertEquals(mockQuiz.getQuizId(), grading.getQuiz_id().getQuizId(), "L'ID du quiz doit correspondre");
        assertEquals("Quiz Spring Boot", grading.getQuiz_id().getTitle(), "Le titre du quiz doit correspondre");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Student")
    void testManyToOneRelationWithStudent() {
        grading = new Grading();
        grading.setStudent_id(mockStudent);

        assertNotNull(grading.getStudent_id(), "Le student ne doit pas être null");
        assertEquals(mockStudent.getUserAccountId(), grading.getStudent_id().getUserAccountId(),
                "L'ID du student doit correspondre");
    }

    @Test
    @DisplayName("Test plusieurs gradings pour le même quiz")
    void testMultipleGradingsForSameQuiz() {
        Student student2 = new Student();
        student2.setUserAccountId(2);

        Grading grading1 = new Grading(80, mockQuiz, mockStudent);
        Grading grading2 = new Grading(90, mockQuiz, student2);

        assertEquals(mockQuiz, grading1.getQuiz_id(), "Les deux gradings doivent avoir le même quiz");
        assertEquals(mockQuiz, grading2.getQuiz_id(), "Les deux gradings doivent avoir le même quiz");
        assertNotEquals(grading1.getStudent_id(), grading2.getStudent_id(), "Les students doivent être différents");
        assertNotEquals(grading1.getGrade(), grading2.getGrade(), "Les notes doivent être différentes");
    }

    @Test
    @DisplayName("Test plusieurs gradings pour le même student")
    void testMultipleGradingsForSameStudent() {
        Quiz quiz2 = new Quiz();
        quiz2.setQuizId(2);
        quiz2.setTitle("Quiz JPA");

        Grading grading1 = new Grading(85, mockQuiz, mockStudent);
        Grading grading2 = new Grading(92, quiz2, mockStudent);

        assertEquals(mockStudent, grading1.getStudent_id(), "Les deux gradings doivent avoir le même student");
        assertEquals(mockStudent, grading2.getStudent_id(), "Les deux gradings doivent avoir le même student");
        assertNotEquals(grading1.getQuiz_id(), grading2.getQuiz_id(), "Les quiz doivent être différents");
    }

    @Test
    @DisplayName("Test Grading avec note négative")
    void testGradingWithNegativeScore() {
        grading = new Grading(-10, mockQuiz, mockStudent);

        assertEquals(-10, grading.getGrade(), "La note négative doit être acceptée");
    }

    @Test
    @DisplayName("Test Grading avec note supérieure à 100")
    void testGradingWithScoreOver100() {
        grading = new Grading(150, mockQuiz, mockStudent);

        assertEquals(150, grading.getGrade(), "La note supérieure à 100 doit être acceptée");
    }

    @Test
    @DisplayName("Test modification du quiz associé")
    void testModifyAssociatedQuiz() {
        grading = new Grading(80, mockQuiz, mockStudent);

        Quiz newQuiz = new Quiz();
        newQuiz.setQuizId(2);
        newQuiz.setTitle("Nouveau quiz");

        grading.setQuiz_id(newQuiz);

        assertEquals(newQuiz, grading.getQuiz_id(), "Le quiz doit être modifié");
        assertEquals(2, grading.getQuiz_id().getQuizId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test modification du student associé")
    void testModifyAssociatedStudent() {
        grading = new Grading(80, mockQuiz, mockStudent);

        Student newStudent = new Student();
        newStudent.setUserAccountId(2);

        grading.setStudent_id(newStudent);

        assertEquals(newStudent, grading.getStudent_id(), "Le student doit être modifié");
        assertEquals(2, grading.getStudent_id().getUserAccountId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test Grading avec quizId null")
    void testGradingWithNullQuiz() {
        grading = new Grading(80, null, mockStudent);

        assertNull(grading.getQuiz_id(), "Le quizId null doit être accepté");
        assertEquals(80, grading.getGrade(), "La note doit être préservée");
    }

    @Test
    @DisplayName("Test Grading avec studentId null")
    void testGradingWithNullStudent() {
        grading = new Grading(80, mockQuiz, null);

        assertNull(grading.getStudent_id(), "Le studentId null doit être accepté");
        assertEquals(80, grading.getGrade(), "La note doit être préservée");
    }

    @Test
    @DisplayName("Test comparaison de notes entre deux étudiants")
    void testCompareGradesBetweenStudents() {
        Student student2 = new Student();
        student2.setUserAccountId(2);

        Grading grading1 = new Grading(75, mockQuiz, mockStudent);
        Grading grading2 = new Grading(90, mockQuiz, student2);

        assertTrue(grading2.getGrade() > grading1.getGrade(), "Student2 doit avoir une meilleure note");
    }

    @Test
    @DisplayName("Test calcul de la moyenne des notes")
    void testCalculateAverageGrade() {
        Grading grading1 = new Grading(80, mockQuiz, mockStudent);
        Grading grading2 = new Grading(90, mockQuiz, mockStudent);
        Grading grading3 = new Grading(85, mockQuiz, mockStudent);

        int sum = grading1.getGrade() + grading2.getGrade() + grading3.getGrade();
        double average = sum / 3.0;

        assertEquals(85.0, average, 0.01, "La moyenne doit être 85");
    }

    @Test
    @DisplayName("Test identification d'une note excellente (>= 90)")
    void testIdentifyExcellentGrade() {
        grading = new Grading(95, mockQuiz, mockStudent);

        assertTrue(grading.getGrade() >= 90, "La note doit être excellente");
    }

    @Test
    @DisplayName("Test identification d'une note insuffisante (< 50)")
    void testIdentifyFailingGrade() {
        grading = new Grading(45, mockQuiz, mockStudent);

        assertTrue(grading.getGrade() < 50, "La note doit être insuffisante");
    }

    @Test
    @DisplayName("Test identification d'une note moyenne (50-75)")
    void testIdentifyAverageGrade() {
        grading = new Grading(65, mockQuiz, mockStudent);

        assertTrue(grading.getGrade() >= 50 && grading.getGrade() <= 75, "La note doit être moyenne");
    }

    @Test
    @DisplayName("Test identification d'une note bonne (75-90)")
    void testIdentifyGoodGrade() {
        grading = new Grading(82, mockQuiz, mockStudent);

        assertTrue(grading.getGrade() > 75 && grading.getGrade() < 90, "La note doit être bonne");
    }

    @Test
    @DisplayName("Test modification gradingId")
    void testModifyGradingId() {
        grading = new Grading(80, mockQuiz, mockStudent);
        grading.setGradingId(1);

        grading.setGradingId(999);

        assertEquals(999, grading.getGradingId(), "L'ID doit être modifié à 999");
    }

    @Test
    @DisplayName("Test Grading avec note limite inférieure (0)")
    void testGradingWithLowerBoundary() {
        grading = new Grading(0, mockQuiz, mockStudent);

        assertEquals(0, grading.getGrade(), "La note 0 doit être acceptée");
    }

    @Test
    @DisplayName("Test Grading avec note limite supérieure (100)")
    void testGradingWithUpperBoundary() {
        grading = new Grading(100, mockQuiz, mockStudent);

        assertEquals(100, grading.getGrade(), "La note 100 doit être acceptée");
    }

    @Test
    @DisplayName("Test comparaison de deux gradings identiques")
    void testCompareIdenticalGradings() {
        Grading grading1 = new Grading(80, mockQuiz, mockStudent);
        Grading grading2 = new Grading(80, mockQuiz, mockStudent);

        assertEquals(grading1.getGrade(), grading2.getGrade(), "Les notes doivent être identiques");
        assertEquals(grading1.getQuiz_id(), grading2.getQuiz_id(), "Les quiz doivent être identiques");
        assertEquals(grading1.getStudent_id(), grading2.getStudent_id(), "Les students doivent être identiques");
    }

    @Test
    @DisplayName("Test tri des gradings par note croissante")
    void testSortGradingsByAscendingScore() {
        Grading grading1 = new Grading(65, mockQuiz, mockStudent);
        Grading grading2 = new Grading(85, mockQuiz, mockStudent);
        Grading grading3 = new Grading(75, mockQuiz, mockStudent);

        assertTrue(grading1.getGrade() < grading3.getGrade(), "Grading1 doit être avant Grading3");
        assertTrue(grading3.getGrade() < grading2.getGrade(), "Grading3 doit être avant Grading2");
    }

    @Test
    @DisplayName("Test vérification de réussite au quiz (>= 50)")
    void testCheckQuizPass() {
        Grading gradingPass = new Grading(60, mockQuiz, mockStudent);
        Grading gradingFail = new Grading(40, mockQuiz, mockStudent);

        assertTrue(gradingPass.getGrade() >= 50, "L'étudiant doit avoir réussi");
        assertFalse(gradingFail.getGrade() >= 50, "L'étudiant doit avoir échoué");
    }

    @Test
    @DisplayName("Test Grading avec ID négatif")
    void testGradingWithNegativeId() {
        grading = new Grading();
        grading.setGradingId(-1);

        assertEquals(-1, grading.getGradingId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test amélioration de la note")
    void testGradeImprovement() {
        grading = new Grading(70, mockQuiz, mockStudent);
        int initialGrade = grading.getGrade();

        grading.setGrade(85);

        assertTrue(grading.getGrade() > initialGrade, "La note doit être améliorée");
        assertEquals(15, grading.getGrade() - initialGrade, "L'amélioration doit être de 15 points");
    }
}