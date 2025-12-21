package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static com.LMS.Learning_Management_System.entity.QuestionType.QuestionTypeEnum.*;

@DisplayName("QuestionType Entity Tests")
class QuestionTypeTest {

    private QuestionType questionType;

    @BeforeEach
    void setUp() {
        // Initialisation avant chaque test
    }

    @Test
    @DisplayName("Test création QuestionType avec constructeur par défaut")
    void testDefaultConstructor() {
        questionType = new QuestionType();

        assertNotNull(questionType, "Le questionType ne doit pas être null");
        assertEquals(0, questionType.getTypeId(), "L'ID par défaut doit être 0");
        assertNull(questionType.getTypeName(), "Le typeName par défaut doit être null");
    }

    @Test
    @DisplayName("Test création QuestionType avec constructeur complet")
    void testFullConstructor() {
        questionType = new QuestionType(1, MCQ);

        assertNotNull(questionType, "Le questionType ne doit pas être null");
        assertEquals(1, questionType.getTypeId(), "L'ID doit être 1");
        assertEquals(MCQ, questionType.getTypeName(), "Le type doit être MCQ");
    }

    @Test
    @DisplayName("Test setters et getters - TypeId")
    void testTypeIdSetterGetter() {
        questionType = new QuestionType();
        questionType.setTypeId(100);

        assertEquals(100, questionType.getTypeId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - TypeName MCQ")
    void testTypeNameSetterGetterMCQ() {
        questionType = new QuestionType();
        questionType.setTypeName(MCQ);

        assertEquals(MCQ, questionType.getTypeName(), "Le type doit être MCQ");
    }

    @Test
    @DisplayName("Test setters et getters - TypeName TRUE_FALSE")
    void testTypeNameSetterGetterTrueFalse() {
        questionType = new QuestionType();
        questionType.setTypeName(TRUE_FALSE);

        assertEquals(TRUE_FALSE, questionType.getTypeName(), "Le type doit être TRUE_FALSE");
    }

    @Test
    @DisplayName("Test setters et getters - TypeName SHORT_ANSWER")
    void testTypeNameSetterGetterShortAnswer() {
        questionType = new QuestionType();
        questionType.setTypeName(SHORT_ANSWER);

        assertEquals(SHORT_ANSWER, questionType.getTypeName(), "Le type doit être SHORT_ANSWER");
    }

    @Test
    @DisplayName("Test création QuestionType avec type MCQ")
    void testCreateMCQType() {
        questionType = new QuestionType(1, MCQ);

        assertEquals(MCQ, questionType.getTypeName(), "Le type doit être MCQ");
        assertEquals("MCQ", questionType.getTypeName().name(), "Le nom du type doit être MCQ");
    }

    @Test
    @DisplayName("Test création QuestionType avec type TRUE_FALSE")
    void testCreateTrueFalseType() {
        questionType = new QuestionType(2, TRUE_FALSE);

        assertEquals(TRUE_FALSE, questionType.getTypeName(), "Le type doit être TRUE_FALSE");
        assertEquals("TRUE_FALSE", questionType.getTypeName().name(), "Le nom du type doit être TRUE_FALSE");
    }

    @Test
    @DisplayName("Test création QuestionType avec type SHORT_ANSWER")
    void testCreateShortAnswerType() {
        questionType = new QuestionType(3, SHORT_ANSWER);

        assertEquals(SHORT_ANSWER, questionType.getTypeName(), "Le type doit être SHORT_ANSWER");
        assertEquals("SHORT_ANSWER", questionType.getTypeName().name(), "Le nom du type doit être SHORT_ANSWER");
    }

    @Test
    @DisplayName("Test modification du type")
    void testModifyTypeName() {
        questionType = new QuestionType(1, MCQ);

        questionType.setTypeName(TRUE_FALSE);

        assertEquals(TRUE_FALSE, questionType.getTypeName(), "Le type doit être modifié à TRUE_FALSE");
        assertNotEquals(MCQ, questionType.getTypeName(), "Le type ne doit plus être MCQ");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes pour MCQ")
    void testToStringMCQ() {
        questionType = new QuestionType(1, MCQ);

        String result = questionType.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("typeId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("typeName=MCQ"), "toString doit contenir le type MCQ");
        assertTrue(result.contains("QuestionType{"), "toString doit commencer par QuestionType{");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes pour TRUE_FALSE")
    void testToStringTrueFalse() {
        questionType = new QuestionType(2, TRUE_FALSE);

        String result = questionType.toString();

        assertTrue(result.contains("typeId=2"), "toString doit contenir l'ID");
        assertTrue(result.contains("typeName=TRUE_FALSE"), "toString doit contenir le type TRUE_FALSE");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes pour SHORT_ANSWER")
    void testToStringShortAnswer() {
        questionType = new QuestionType(3, SHORT_ANSWER);

        String result = questionType.toString();

        assertTrue(result.contains("typeId=3"), "toString doit contenir l'ID");
        assertTrue(result.contains("typeName=SHORT_ANSWER"), "toString doit contenir le type SHORT_ANSWER");
    }

    @Test
    @DisplayName("Test QuestionType avec typeName null")
    void testQuestionTypeWithNullTypeName() {
        questionType = new QuestionType(1, null);

        assertNull(questionType.getTypeName(), "Le typeName null doit être accepté");
    }

    @Test
    @DisplayName("Test vérification de tous les types enum disponibles")
    void testAllEnumValues() {
        QuestionType.QuestionTypeEnum[] allTypes = QuestionType.QuestionTypeEnum.values();

        assertEquals(3, allTypes.length, "Il doit y avoir 3 types de questions");
        assertEquals(MCQ, allTypes[0], "Le premier type doit être MCQ");
        assertEquals(TRUE_FALSE, allTypes[1], "Le deuxième type doit être TRUE_FALSE");
        assertEquals(SHORT_ANSWER, allTypes[2], "Le troisième type doit être SHORT_ANSWER");
    }

    @Test
    @DisplayName("Test valueOf pour MCQ")
    void testValueOfMCQ() {
        QuestionType.QuestionTypeEnum type = QuestionType.QuestionTypeEnum.valueOf("MCQ");

        assertEquals(MCQ, type, "Le type doit être MCQ");
    }

    @Test
    @DisplayName("Test valueOf pour TRUE_FALSE")
    void testValueOfTrueFalse() {
        QuestionType.QuestionTypeEnum type = QuestionType.QuestionTypeEnum.valueOf("TRUE_FALSE");

        assertEquals(TRUE_FALSE, type, "Le type doit être TRUE_FALSE");
    }

    @Test
    @DisplayName("Test valueOf pour SHORT_ANSWER")
    void testValueOfShortAnswer() {
        QuestionType.QuestionTypeEnum type = QuestionType.QuestionTypeEnum.valueOf("SHORT_ANSWER");

        assertEquals(SHORT_ANSWER, type, "Le type doit être SHORT_ANSWER");
    }

    @Test
    @DisplayName("Test valueOf avec valeur invalide lève une exception")
    void testValueOfInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            QuestionType.QuestionTypeEnum.valueOf("INVALID_TYPE");
        }, "Une exception doit être levée pour un type invalide");
    }

    @Test
    @DisplayName("Test comparaison de deux QuestionType identiques")
    void testCompareIdenticalQuestionTypes() {
        QuestionType type1 = new QuestionType(1, MCQ);
        QuestionType type2 = new QuestionType(1, MCQ);

        assertEquals(type1.getTypeId(), type2.getTypeId(), "Les IDs doivent être identiques");
        assertEquals(type1.getTypeName(), type2.getTypeName(), "Les types doivent être identiques");
    }

    @Test
    @DisplayName("Test comparaison de deux QuestionType différents")
    void testCompareDifferentQuestionTypes() {
        QuestionType type1 = new QuestionType(1, MCQ);
        QuestionType type2 = new QuestionType(2, TRUE_FALSE);

        assertNotEquals(type1.getTypeId(), type2.getTypeId(), "Les IDs doivent être différents");
        assertNotEquals(type1.getTypeName(), type2.getTypeName(), "Les types doivent être différents");
    }

    @Test
    @DisplayName("Test modification du typeId")
    void testModifyTypeId() {
        questionType = new QuestionType(1, MCQ);

        questionType.setTypeId(10);

        assertEquals(10, questionType.getTypeId(), "L'ID doit être modifié à 10");
        assertNotEquals(1, questionType.getTypeId(), "L'ID ne doit plus être 1");
    }

    @Test
    @DisplayName("Test QuestionType avec ID négatif")
    void testQuestionTypeWithNegativeId() {
        questionType = new QuestionType(-1, MCQ);

        assertEquals(-1, questionType.getTypeId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test QuestionType avec ID zéro")
    void testQuestionTypeWithZeroId() {
        questionType = new QuestionType(0, MCQ);

        assertEquals(0, questionType.getTypeId(), "L'ID zéro doit être accepté");
    }

    @Test
    @DisplayName("Test name() sur chaque enum")
    void testEnumNames() {
        assertEquals("MCQ", MCQ.name(), "Le nom doit être MCQ");
        assertEquals("TRUE_FALSE", TRUE_FALSE.name(), "Le nom doit être TRUE_FALSE");
        assertEquals("SHORT_ANSWER", SHORT_ANSWER.name(), "Le nom doit être SHORT_ANSWER");
    }

    @Test
    @DisplayName("Test ordinal() sur chaque enum")
    void testEnumOrdinals() {
        assertEquals(0, MCQ.ordinal(), "MCQ doit avoir l'ordinal 0");
        assertEquals(1, TRUE_FALSE.ordinal(), "TRUE_FALSE doit avoir l'ordinal 1");
        assertEquals(2, SHORT_ANSWER.ordinal(), "SHORT_ANSWER doit avoir l'ordinal 2");
    }

    @Test
    @DisplayName("Test création de plusieurs QuestionType avec différents types")
    void testCreateMultipleQuestionTypes() {
        QuestionType type1 = new QuestionType(1, MCQ);
        QuestionType type2 = new QuestionType(2, TRUE_FALSE);
        QuestionType type3 = new QuestionType(3, SHORT_ANSWER);

        assertEquals(MCQ, type1.getTypeName(), "Le type1 doit être MCQ");
        assertEquals(TRUE_FALSE, type2.getTypeName(), "Le type2 doit être TRUE_FALSE");
        assertEquals(SHORT_ANSWER, type3.getTypeName(), "Le type3 doit être SHORT_ANSWER");

        assertNotEquals(type1.getTypeId(), type2.getTypeId(), "Les IDs doivent être différents");
        assertNotEquals(type2.getTypeId(), type3.getTypeId(), "Les IDs doivent être différents");
    }

    @Test
    @DisplayName("Test switch sur QuestionTypeEnum")
    void testSwitchOnQuestionTypeEnum() {
        questionType = new QuestionType(1, MCQ);

        String result = switch (questionType.getTypeName()) {
            case MCQ -> "Questions à choix multiples";
            case TRUE_FALSE -> "Questions vrai/faux";
            case SHORT_ANSWER -> "Questions à réponse courte";
        };

        assertEquals("Questions à choix multiples", result, "Le résultat doit correspondre à MCQ");
    }

    @Test
    @DisplayName("Test enum equals")
    void testEnumEquals() {
        assertTrue(MCQ.equals(MCQ), "MCQ doit être égal à MCQ");
        assertFalse(MCQ.equals(TRUE_FALSE), "MCQ ne doit pas être égal à TRUE_FALSE");
        assertFalse(TRUE_FALSE.equals(SHORT_ANSWER), "TRUE_FALSE ne doit pas être égal à SHORT_ANSWER");
    }
}