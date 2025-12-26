package com.LMS.Learning_Management_System.dto;

import com.LMS.Learning_Management_System.entity.Grading;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GradingDtoTest {

    private GradingDto gradingDto;

    @BeforeEach
    void setUp() {
        gradingDto = new GradingDto();
    }

    @Test
    void testGetAndSetQuizId() {
        int expectedQuizId = 15;
        gradingDto.setQuiz_id(expectedQuizId);
        assertEquals(expectedQuizId, gradingDto.getQuiz_id());
    }

    @Test
    void testGetAndSetAnswers() {
        List<String> expectedAnswers = Arrays.asList("A", "B", "C", "D");
        gradingDto.setAnswers(expectedAnswers);
        assertEquals(expectedAnswers, gradingDto.getAnswers());
        assertEquals(4, gradingDto.getAnswers().size());
    }

    @Test
    void testGetAndSetStudentId() {
        int expectedStudentId = 250;
        gradingDto.setStudent_id(expectedStudentId);
        assertEquals(expectedStudentId, gradingDto.getStudent_id());
    }

    @Test
    void testGetAndSetGrades() {
        int expectedGrades = 85;
        gradingDto.setGrades(expectedGrades);
        assertEquals(expectedGrades, gradingDto.getGrades());
    }

    @Test
    void testGetAndSetAllGrades() {
        List<Grading> expectedGrades = new ArrayList<>();
        gradingDto.setAllGrades(expectedGrades);
        assertEquals(expectedGrades, gradingDto.getAllGrades());
    }

    @Test
    void testGradingDtoWithAllFields() {
        List<String> answers = Arrays.asList("True", "False", "True");
        List<Grading> allGrades = new ArrayList<>();
        
        gradingDto.setQuiz_id(100);
        gradingDto.setAnswers(answers);
        gradingDto.setStudent_id(500);
        gradingDto.setGrades(95);
        gradingDto.setAllGrades(allGrades);

        assertEquals(100, gradingDto.getQuiz_id());
        assertEquals(answers, gradingDto.getAnswers());
        assertEquals(500, gradingDto.getStudent_id());
        assertEquals(95, gradingDto.getGrades());
        assertEquals(allGrades, gradingDto.getAllGrades());
    }

    @Test
    void testAnswersWithEmptyList() {
        List<String> emptyList = new ArrayList<>();
        gradingDto.setAnswers(emptyList);
        assertNotNull(gradingDto.getAnswers());
        assertEquals(0, gradingDto.getAnswers().size());
    }

    @Test
    void testAnswersWithNullValue() {
        gradingDto.setAnswers(null);
        assertNull(gradingDto.getAnswers());
    }

    @Test
    void testAllGradesWithNullValue() {
        gradingDto.setAllGrades(null);
        assertNull(gradingDto.getAllGrades());
    }

    @Test
    void testGradesWithZeroValue() {
        gradingDto.setGrades(0);
        assertEquals(0, gradingDto.getGrades());
    }

    @Test
    void testGradesWithNegativeValue() {
        gradingDto.setGrades(-5);
        assertEquals(-5, gradingDto.getGrades());
    }

    @Test
    void testQuizIdWithNegativeValue() {
        gradingDto.setQuiz_id(-1);
        assertEquals(-1, gradingDto.getQuiz_id());
    }

    @Test
    void testStudentIdWithZeroValue() {
        gradingDto.setStudent_id(0);
        assertEquals(0, gradingDto.getStudent_id());
    }

    @Test
    void testAnswersWithMultipleValues() {
        List<String> answers = Arrays.asList("Option1", "Option2", "Option3", "Option4", "Option5");
        gradingDto.setAnswers(answers);
        assertEquals(5, gradingDto.getAnswers().size());
        assertEquals("Option1", gradingDto.getAnswers().get(0));
        assertEquals("Option5", gradingDto.getAnswers().get(4));
    }

    @Test
    void testModifyingAnswersList() {
        List<String> answers = new ArrayList<>(Arrays.asList("A", "B", "C"));
        gradingDto.setAnswers(answers);
        
        answers.add("D");
        assertEquals(4, gradingDto.getAnswers().size());
    }

    @Test
    void testDefaultValues() {
        GradingDto newDto = new GradingDto();
        assertEquals(0, newDto.getQuiz_id());
        assertEquals(0, newDto.getStudent_id());
        assertEquals(0, newDto.getGrades());
        assertNull(newDto.getAnswers());
        assertNull(newDto.getAllGrades());
    }
}