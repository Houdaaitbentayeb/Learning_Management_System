package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Answer Entity Tests")
class AnswerTest {

    private Answer answer;
    private Question mockQuestion;
    private Student mockStudent;

    @BeforeEach
    void setUp() {
        // Création de mocks pour les tests
        mockQuestion = new Question();
        mockQuestion.setQuestionId(1);
        mockQuestion.setQuestionText("Qu'est-ce que Spring Boot?");

        mockStudent = new Student();
        mockStudent.setUserAccountId(1);
    }

    @Test
    @DisplayName("Test création Answer avec constructeur par défaut")
    void testDefaultConstructor() {
        answer = new Answer();

        assertNotNull(answer, "L'answer ne doit pas être null");
        assertEquals(0, answer.getAnswerId(), "L'ID par défaut doit être 0");
        assertNull(answer.getAnswerText(), "Le texte par défaut doit être null");
        assertNull(answer.getIsCorrect(), "isCorrect par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Answer avec constructeur complet")
    void testFullConstructor() {
        answer = new Answer(1, mockQuestion, mockStudent, "Spring Boot est un framework", true);

        assertNotNull(answer, "L'answer ne doit pas être null");
        assertEquals(1, answer.getAnswerId(), "L'ID doit être 1");
        assertEquals(mockQuestion, answer.getQuestion(), "La question doit correspondre");
        assertEquals(mockStudent, answer.getStudent(), "L'étudiant doit correspondre");
        assertEquals("Spring Boot est un framework", answer.getAnswerText(), "Le texte doit correspondre");
        assertTrue(answer.getIsCorrect(), "La réponse doit être correcte");
    }

    @Test
    @DisplayName("Test setters et getters - AnswerId")
    void testAnswerIdSetterGetter() {
        answer = new Answer();
        answer.setAnswerId(100);

        assertEquals(100, answer.getAnswerId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - Question")
    void testQuestionSetterGetter() {
        answer = new Answer();
        answer.setQuestion(mockQuestion);

        assertEquals(mockQuestion, answer.getQuestion(), "La question doit correspondre");
        assertEquals(1, answer.getQuestion().getQuestionId(), "L'ID de la question doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - Student")
    void testStudentSetterGetter() {
        answer = new Answer();
        answer.setStudent(mockStudent);

        assertEquals(mockStudent, answer.getStudent(), "L'étudiant doit correspondre");
        assertEquals(1, answer.getStudent().getUserAccountId(), "L'ID de l'étudiant doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - AnswerText")
    void testAnswerTextSetterGetter() {
        answer = new Answer();
        answer.setAnswerText("Ma réponse ici");

        assertEquals("Ma réponse ici", answer.getAnswerText(), "Le texte doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - IsCorrect true")
    void testIsCorrectSetterGetterTrue() {
        answer = new Answer();
        answer.setIsCorrect(true);

        assertTrue(answer.getIsCorrect(), "isCorrect doit être true");
    }

    @Test
    @DisplayName("Test setters et getters - IsCorrect false")
    void testIsCorrectSetterGetterFalse() {
        answer = new Answer();
        answer.setIsCorrect(false);

        assertFalse(answer.getIsCorrect(), "isCorrect doit être false");
    }

    @Test
    @DisplayName("Test création réponse correcte")
    void testCreateCorrectAnswer() {
        answer = new Answer(1, mockQuestion, mockStudent, "Réponse correcte", true);

        assertTrue(answer.getIsCorrect(), "La réponse doit être marquée comme correcte");
        assertNotNull(answer.getAnswerText(), "Le texte ne doit pas être null");
    }

    @Test
    @DisplayName("Test création réponse incorrecte")
    void testCreateIncorrectAnswer() {
        answer = new Answer(2, mockQuestion, mockStudent, "Réponse incorrecte", false);

        assertFalse(answer.getIsCorrect(), "La réponse doit être marquée comme incorrecte");
        assertEquals("Réponse incorrecte", answer.getAnswerText(), "Le texte doit correspondre");
    }

    @Test
    @DisplayName("Test modification du texte de réponse")
    void testModifyAnswerText() {
        answer = new Answer(1, mockQuestion, mockStudent, "Texte initial", true);

        answer.setAnswerText("Texte modifié");

        assertEquals("Texte modifié", answer.getAnswerText(), "Le texte doit être modifié");
        assertTrue(answer.getIsCorrect(), "isCorrect ne doit pas changer");
    }

    @Test
    @DisplayName("Test modification du statut correct/incorrect")
    void testModifyIsCorrect() {
        answer = new Answer(1, mockQuestion, mockStudent, "Ma réponse", true);

        answer.setIsCorrect(false);

        assertFalse(answer.getIsCorrect(), "isCorrect doit être modifié à false");
        assertEquals("Ma réponse", answer.getAnswerText(), "Le texte ne doit pas changer");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Question")
    void testManyToOneRelationWithQuestion() {
        answer = new Answer();
        answer.setQuestion(mockQuestion);

        assertNotNull(answer.getQuestion(), "La question ne doit pas être null");
        assertEquals(mockQuestion.getQuestionId(), answer.getQuestion().getQuestionId(), "L'ID de la question doit correspondre");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Student")
    void testManyToOneRelationWithStudent() {
        answer = new Answer();
        answer.setStudent(mockStudent);

        assertNotNull(answer.getStudent(), "L'étudiant ne doit pas être null");
        assertEquals(mockStudent.getUserAccountId(), answer.getStudent().getUserAccountId(), "L'ID de l'étudiant doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        answer = new Answer(1, mockQuestion, mockStudent, "Test answer", true);

        String result = answer.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("answerId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("answerText='Test answer'"), "toString doit contenir le texte");
        assertTrue(result.contains("isCorrect=true"), "toString doit contenir isCorrect");
        assertTrue(result.contains("Answer{"), "toString doit commencer par Answer{");
    }

    @Test
    @DisplayName("Test Answer avec answerText vide")
    void testAnswerWithEmptyText() {
        answer = new Answer(1, mockQuestion, mockStudent, "", true);

        assertEquals("", answer.getAnswerText(), "Le texte vide doit être accepté");
        assertTrue(answer.getIsCorrect(), "isCorrect doit être préservé");
    }

    @Test
    @DisplayName("Test Answer avec answerText null")
    void testAnswerWithNullText() {
        answer = new Answer(1, mockQuestion, mockStudent, null, true);

        assertNull(answer.getAnswerText(), "Le texte null doit être accepté");
    }

    @Test
    @DisplayName("Test Answer avec isCorrect null")
    void testAnswerWithNullIsCorrect() {
        answer = new Answer(1, mockQuestion, mockStudent, "Texte", null);

        assertNull(answer.getIsCorrect(), "isCorrect null doit être accepté");
    }

    @Test
    @DisplayName("Test Answer avec texte long")
    void testAnswerWithLongText() {
        String longText = "Ceci est une très longue réponse qui contient beaucoup d'informations " +
                "détaillées sur le sujet en question et qui pourrait être utilisée " +
                "pour tester la limite de caractères acceptée par la base de données.";

        answer = new Answer(1, mockQuestion, mockStudent, longText, true);

        assertEquals(longText, answer.getAnswerText(), "Le texte long doit être accepté");
        assertTrue(answer.getAnswerText().length() > 100, "Le texte doit être long");
    }

    @Test
    @DisplayName("Test modification de la question associée")
    void testModifyAssociatedQuestion() {
        answer = new Answer(1, mockQuestion, mockStudent, "Réponse", true);

        Question newQuestion = new Question();
        newQuestion.setQuestionId(2);
        newQuestion.setQuestionText("Nouvelle question?");

        answer.setQuestion(newQuestion);

        assertEquals(newQuestion, answer.getQuestion(), "La question doit être modifiée");
        assertEquals(2, answer.getQuestion().getQuestionId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test modification de l'étudiant associé")
    void testModifyAssociatedStudent() {
        answer = new Answer(1, mockQuestion, mockStudent, "Réponse", true);

        Student newStudent = new Student();
        newStudent.setUserAccountId(2);

        answer.setStudent(newStudent);

        assertEquals(newStudent, answer.getStudent(), "L'étudiant doit être modifié");
        assertEquals(2, answer.getStudent().getUserAccountId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test Answer avec caractères spéciaux dans le texte")
    void testAnswerWithSpecialCharacters() {
        String specialText = "Réponse avec des caractères spéciaux: é, à, ç, €, @, #, %";
        answer = new Answer(1, mockQuestion, mockStudent, specialText, true);

        assertEquals(specialText, answer.getAnswerText(), "Les caractères spéciaux doivent être acceptés");
    }

    @Test
    @DisplayName("Test plusieurs réponses pour une même question")
    void testMultipleAnswersForSameQuestion() {
        Answer answer1 = new Answer(1, mockQuestion, mockStudent, "Réponse 1", true);
        Answer answer2 = new Answer(2, mockQuestion, mockStudent, "Réponse 2", false);

        assertEquals(mockQuestion, answer1.getQuestion(), "Les deux réponses doivent avoir la même question");
        assertEquals(mockQuestion, answer2.getQuestion(), "Les deux réponses doivent avoir la même question");
        assertNotEquals(answer1.getAnswerId(), answer2.getAnswerId(), "Les IDs doivent être différents");
    }
}