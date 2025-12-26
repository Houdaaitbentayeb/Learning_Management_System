package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static com.LMS.Learning_Management_System.entity.QuestionType.QuestionTypeEnum.*;

@DisplayName("Question Entity Tests")
class QuestionTest {

    private Question question;
    private Quiz mockQuiz;
    private QuestionType mockQuestionType;
    private Course mockCourse;

    @BeforeEach
    void setUp() {
        // Création d'un mock quiz
        mockQuiz = new Quiz();
        mockQuiz.setQuizId(1);
        mockQuiz.setTitle("Quiz Spring Boot");

        // Création d'un mock question type
        mockQuestionType = new QuestionType();
        mockQuestionType.setTypeId(1);
        mockQuestionType.setTypeName(MCQ);

        // Création d'un mock course
        mockCourse = new Course();
        mockCourse.setCourseId(1);
        mockCourse.setCourseName("Spring Boot Avancé");
    }

    @Test
    @DisplayName("Test création Question avec constructeur par défaut")
    void testDefaultConstructor() {
        question = new Question();

        assertNotNull(question, "La question ne doit pas être null");
        assertEquals(0, question.getQuestionId(), "L'ID par défaut doit être 0");
        assertNull(question.getQuestionText(), "Le texte par défaut doit être null");
        assertNull(question.getOptions(), "Les options par défaut doivent être null");
        assertNull(question.getCorrectAnswer(), "La réponse correcte par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Question avec constructeur complet (avec ID)")
    void testFullConstructorWithId() {
        String options = "[\"Option A\", \"Option B\", \"Option C\", \"Option D\"]";
        question = new Question(1, mockQuiz, "Qu'est-ce que Spring Boot?", mockQuestionType, options, "Option A");

        assertNotNull(question, "La question ne doit pas être null");
        assertEquals(1, question.getQuestionId(), "L'ID doit être 1");
        assertEquals(mockQuiz, question.getQuiz(), "Le quiz doit correspondre");
        assertEquals("Qu'est-ce que Spring Boot?", question.getQuestionText(), "Le texte doit correspondre");
        assertEquals(mockQuestionType, question.getQuestionType(), "Le type doit correspondre");
        assertEquals(options, question.getOptions(), "Les options doivent correspondre");
        assertEquals("Option A", question.getCorrectAnswer(), "La réponse correcte doit correspondre");
    }

    @Test
    @DisplayName("Test création Question avec constructeur sans ID")
    void testConstructorWithoutId() {
        String options = "[\"Vrai\", \"Faux\"]";
        question = new Question(mockQuiz, "Spring Boot simplifie le développement?", mockQuestionType, options, "Vrai");

        assertNotNull(question, "La question ne doit pas être null");
        assertEquals(0, question.getQuestionId(), "L'ID par défaut doit être 0");
        assertEquals(mockQuiz, question.getQuiz(), "Le quiz doit correspondre");
        assertEquals("Spring Boot simplifie le développement?", question.getQuestionText(), "Le texte doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - QuestionId")
    void testQuestionIdSetterGetter() {
        question = new Question();
        question.setQuestionId(100);

        assertEquals(100, question.getQuestionId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - Quiz")
    void testQuizSetterGetter() {
        question = new Question();
        question.setQuiz(mockQuiz);

        assertEquals(mockQuiz, question.getQuiz(), "Le quiz doit correspondre");
        assertEquals(1, question.getQuiz().getQuizId(), "L'ID du quiz doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - QuestionText")
    void testQuestionTextSetterGetter() {
        question = new Question();
        question.setQuestionText("Quelle est la capitale de la France?");

        assertEquals("Quelle est la capitale de la France?", question.getQuestionText(), "Le texte doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - QuestionType")
    void testQuestionTypeSetterGetter() {
        question = new Question();
        question.setQuestionType(mockQuestionType);

        assertEquals(mockQuestionType, question.getQuestionType(), "Le type doit correspondre");
        assertEquals(MCQ, question.getQuestionType().getTypeName(), "Le type doit être MCQ");
    }

    @Test
    @DisplayName("Test setters et getters - Options")
    void testOptionsSetterGetter() {
        question = new Question();
        String options = "[\"A\", \"B\", \"C\", \"D\"]";
        question.setOptions(options);

        assertEquals(options, question.getOptions(), "Les options doivent correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - CorrectAnswer")
    void testCorrectAnswerSetterGetter() {
        question = new Question();
        question.setCorrectAnswer("Paris");

        assertEquals("Paris", question.getCorrectAnswer(), "La réponse correcte doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - CourseId")
    void testCourseIdSetterGetter() {
        question = new Question();
        question.setCourseId(mockCourse);

        assertEquals(mockCourse, question.getCourseId(), "Le cours doit correspondre");
        assertEquals(1, question.getCourseId().getCourseId(), "L'ID du cours doit être 1");
    }

    @Test
    @DisplayName("Test création question MCQ")
    void testCreateMCQQuestion() {
        QuestionType mcqType = new QuestionType(1, MCQ);
        String options = "[\"Spring\", \"Hibernate\", \"Django\", \"Laravel\"]";
        question = new Question(1, mockQuiz, "Quel est un framework Java?", mcqType, options, "Spring");

        assertEquals(MCQ, question.getQuestionType().getTypeName(), "Le type doit être MCQ");
        assertTrue(question.getOptions().contains("Spring"), "Les options doivent contenir Spring");
        assertEquals("Spring", question.getCorrectAnswer(), "La réponse correcte doit être Spring");
    }

    @Test
    @DisplayName("Test création question TRUE_FALSE")
    void testCreateTrueFalseQuestion() {
        QuestionType trueFalseType = new QuestionType(2, TRUE_FALSE);
        String options = "[\"Vrai\", \"Faux\"]";
        question = new Question(1, mockQuiz, "Java est un langage compilé?", trueFalseType, options, "Vrai");

        assertEquals(TRUE_FALSE, question.getQuestionType().getTypeName(), "Le type doit être TRUE_FALSE");
        assertEquals("Vrai", question.getCorrectAnswer(), "La réponse correcte doit être Vrai");
    }

    @Test
    @DisplayName("Test création question SHORT_ANSWER")
    void testCreateShortAnswerQuestion() {
        QuestionType shortAnswerType = new QuestionType(3, SHORT_ANSWER);
        question = new Question(1, mockQuiz, "Quelle est la capitale du Maroc?", shortAnswerType, null, "Rabat");

        assertEquals(SHORT_ANSWER, question.getQuestionType().getTypeName(), "Le type doit être SHORT_ANSWER");
        assertNull(question.getOptions(), "Les options doivent être null pour SHORT_ANSWER");
        assertEquals("Rabat", question.getCorrectAnswer(), "La réponse correcte doit être Rabat");
    }

    @Test
    @DisplayName("Test modification du texte de la question")
    void testModifyQuestionText() {
        question = new Question(1, mockQuiz, "Texte initial", mockQuestionType, "[]", "A");

        question.setQuestionText("Texte modifié");

        assertEquals("Texte modifié", question.getQuestionText(), "Le texte doit être modifié");
    }

    @Test
    @DisplayName("Test modification des options")
    void testModifyOptions() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[\"A\", \"B\"]", "A");

        question.setOptions("[\"X\", \"Y\", \"Z\"]");

        assertEquals("[\"X\", \"Y\", \"Z\"]", question.getOptions(), "Les options doivent être modifiées");
    }

    @Test
    @DisplayName("Test modification de la réponse correcte")
    void testModifyCorrectAnswer() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[\"A\", \"B\"]", "A");

        question.setCorrectAnswer("B");

        assertEquals("B", question.getCorrectAnswer(), "La réponse correcte doit être modifiée");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Quiz")
    void testManyToOneRelationWithQuiz() {
        question = new Question();
        question.setQuiz(mockQuiz);

        assertNotNull(question.getQuiz(), "Le quiz ne doit pas être null");
        assertEquals(mockQuiz.getQuizId(), question.getQuiz().getQuizId(), "L'ID du quiz doit correspondre");
        assertEquals("Quiz Spring Boot", question.getQuiz().getTitle(), "Le titre du quiz doit correspondre");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec QuestionType")
    void testManyToOneRelationWithQuestionType() {
        question = new Question();
        question.setQuestionType(mockQuestionType);

        assertNotNull(question.getQuestionType(), "Le type ne doit pas être null");
        assertEquals(mockQuestionType.getTypeId(), question.getQuestionType().getTypeId(), "L'ID du type doit correspondre");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Course")
    void testManyToOneRelationWithCourse() {
        question = new Question();
        question.setCourseId(mockCourse);

        assertNotNull(question.getCourseId(), "Le cours ne doit pas être null");
        assertEquals(mockCourse.getCourseId(), question.getCourseId().getCourseId(), "L'ID du cours doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        String options = "[\"A\", \"B\"]";
        question = new Question(1, mockQuiz, "Question test", mockQuestionType, options, "A");

        String result = question.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("questionId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("questionText='Question test'"), "toString doit contenir le texte");
        assertTrue(result.contains("correctAnswer='A'"), "toString doit contenir la réponse correcte");
        assertTrue(result.contains("Question{"), "toString doit commencer par Question{");
    }

    @Test
    @DisplayName("Test Question avec questionText vide")
    void testQuestionWithEmptyText() {
        question = new Question(1, mockQuiz, "", mockQuestionType, "[]", "A");

        assertEquals("", question.getQuestionText(), "Le texte vide doit être accepté");
    }

    @Test
    @DisplayName("Test Question avec questionText null")
    void testQuestionWithNullText() {
        question = new Question(1, mockQuiz, null, mockQuestionType, "[]", "A");

        assertNull(question.getQuestionText(), "Le texte null doit être accepté");
    }

    @Test
    @DisplayName("Test Question avec options null")
    void testQuestionWithNullOptions() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, null, "Réponse");

        assertNull(question.getOptions(), "Les options null doivent être acceptées");
    }

    @Test
    @DisplayName("Test Question avec options vides")
    void testQuestionWithEmptyOptions() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[]", "Réponse");

        assertEquals("[]", question.getOptions(), "Les options vides doivent être acceptées");
    }

    @Test
    @DisplayName("Test Question avec correctAnswer null")
    void testQuestionWithNullCorrectAnswer() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[]", null);

        assertNull(question.getCorrectAnswer(), "La réponse correcte null doit être acceptée");
    }

    @Test
    @DisplayName("Test Question avec correctAnswer vide")
    void testQuestionWithEmptyCorrectAnswer() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[]", "");

        assertEquals("", question.getCorrectAnswer(), "La réponse correcte vide doit être acceptée");
    }

    @Test
    @DisplayName("Test Question avec texte long")
    void testQuestionWithLongText() {
        String longText = "Ceci est une très longue question qui contient beaucoup de détails " +
                "et d'informations contextuelles nécessaires pour que l'étudiant " +
                "puisse comprendre exactement ce qui est demandé dans cette question.";
        question = new Question(1, mockQuiz, longText, mockQuestionType, "[]", "Réponse");

        assertEquals(longText, question.getQuestionText(), "Le texte long doit être accepté");
        assertTrue(question.getQuestionText().length() > 100, "Le texte doit être long");
    }

    @Test
    @DisplayName("Test Question avec options JSON complexes")
    void testQuestionWithComplexJSONOptions() {
        String complexOptions = "[{\"id\":1,\"text\":\"Option A\"},{\"id\":2,\"text\":\"Option B\"}]";
        question = new Question(1, mockQuiz, "Question", mockQuestionType, complexOptions, "Option A");

        assertEquals(complexOptions, question.getOptions(), "Les options JSON complexes doivent être acceptées");
        assertTrue(question.getOptions().contains("\"id\""), "Les options doivent contenir des IDs");
    }

    @Test
    @DisplayName("Test modification du quiz associé")
    void testModifyAssociatedQuiz() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[]", "Réponse");

        Quiz newQuiz = new Quiz();
        newQuiz.setQuizId(2);
        newQuiz.setTitle("Nouveau quiz");

        question.setQuiz(newQuiz);

        assertEquals(newQuiz, question.getQuiz(), "Le quiz doit être modifié");
        assertEquals(2, question.getQuiz().getQuizId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test modification du type de question")
    void testModifyQuestionType() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[]", "Réponse");

        QuestionType newType = new QuestionType(2, TRUE_FALSE);
        question.setQuestionType(newType);

        assertEquals(newType, question.getQuestionType(), "Le type doit être modifié");
        assertEquals(TRUE_FALSE, question.getQuestionType().getTypeName(), "Le nouveau type doit être TRUE_FALSE");
    }

    @Test
    @DisplayName("Test modification du cours associé")
    void testModifyAssociatedCourse() {
        question = new Question(1, mockQuiz, "Question", mockQuestionType, "[]", "Réponse");
        question.setCourseId(mockCourse);

        Course newCourse = new Course();
        newCourse.setCourseId(2);
        newCourse.setCourseName("Nouveau cours");

        question.setCourseId(newCourse);

        assertEquals(newCourse, question.getCourseId(), "Le cours doit être modifié");
        assertEquals(2, question.getCourseId().getCourseId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test plusieurs questions pour le même quiz")
    void testMultipleQuestionsForSameQuiz() {
        Question question1 = new Question(1, mockQuiz, "Question 1", mockQuestionType, "[]", "A");
        Question question2 = new Question(2, mockQuiz, "Question 2", mockQuestionType, "[]", "B");

        assertEquals(mockQuiz, question1.getQuiz(), "Les deux questions doivent avoir le même quiz");
        assertEquals(mockQuiz, question2.getQuiz(), "Les deux questions doivent avoir le même quiz");
        assertNotEquals(question1.getQuestionId(), question2.getQuestionId(), "Les IDs doivent être différents");
    }

    @Test
    @DisplayName("Test Question avec caractères spéciaux")
    void testQuestionWithSpecialCharacters() {
        String specialText = "Qu'est-ce que l'injection de dépendances (DI) ?";
        String specialAnswer = "L'IoC & DI";
        question = new Question(1, mockQuiz, specialText, mockQuestionType, "[]", specialAnswer);

        assertEquals(specialText, question.getQuestionText(), "Les caractères spéciaux doivent être acceptés");
        assertEquals(specialAnswer, question.getCorrectAnswer(), "La réponse avec caractères spéciaux doit être acceptée");
    }

    @Test
    @DisplayName("Test Question avec options contenant des guillemets échappés")
    void testQuestionWithEscapedQuotesInOptions() {
        String optionsWithQuotes = "[\"Option \\\"A\\\"\", \"Option \\\"B\\\"\"]";
        question = new Question(1, mockQuiz, "Question", mockQuestionType, optionsWithQuotes, "Option \"A\"");

        assertEquals(optionsWithQuotes, question.getOptions(), "Les options avec guillemets échappés doivent être acceptées");
    }
}