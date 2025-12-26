package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class QuestionDtoTest {

    private QuestionDto questionDto;

    @BeforeEach
    void setUp() {
        questionDto = new QuestionDto();
    }

    @Test
    void testGetAndSetQuestionId() {
        int expectedId = 1;
        questionDto.setQuestion_id(expectedId);
        assertEquals(expectedId, questionDto.getQuestion_id());
    }

    @Test
    void testGetAndSetQuestionText() {
        String expectedText = "What is Java?";
        questionDto.setQuestion_text(expectedText);
        assertEquals(expectedText, questionDto.getQuestion_text());
    }

    @Test
    void testGetAndSetType() {
        int expectedType = 1; // MCQ
        questionDto.setType(expectedType);
        assertEquals(expectedType, questionDto.getType());
    }

    @Test
    void testGetAndSetOptions() {
        String expectedOptions = "[\"Option A\", \"Option B\", \"Option C\"]";
        questionDto.setOptions(expectedOptions);
        assertEquals(expectedOptions, questionDto.getOptions());
    }

    @Test
    void testGetAndSetCourseId() {
        int expectedCourseId = 101;
        questionDto.setCourse_id(expectedCourseId);
        assertEquals(expectedCourseId, questionDto.getCourse_id());
    }

    @Test
    void testGetAndSetCorrectAnswer() {
        String expectedAnswer = "Option A";
        questionDto.setCorrect_answer(expectedAnswer);
        assertEquals(expectedAnswer, questionDto.getCorrect_answer());
    }

    @Test
    void testDefaultValues() {
        assertEquals(0, questionDto.getQuestion_id());
        assertNull(questionDto.getQuestion_text());
        assertEquals(0, questionDto.getType());
        assertNull(questionDto.getOptions());
        assertEquals(0, questionDto.getCourse_id());
        assertNull(questionDto.getCorrect_answer());
    }

    @Test
    void testCompleteQuestionDto() {
        questionDto.setQuestion_id(5);
        questionDto.setQuestion_text("What is polymorphism?");
        questionDto.setType(1);
        questionDto.setOptions("[\"A\", \"B\", \"C\", \"D\"]");
        questionDto.setCourse_id(202);
        questionDto.setCorrect_answer("A");

        assertAll(
                () -> assertEquals(5, questionDto.getQuestion_id()),
                () -> assertEquals("What is polymorphism?", questionDto.getQuestion_text()),
                () -> assertEquals(1, questionDto.getType()),
                () -> assertEquals("[\"A\", \"B\", \"C\", \"D\"]", questionDto.getOptions()),
                () -> assertEquals(202, questionDto.getCourse_id()),
                () -> assertEquals("A", questionDto.getCorrect_answer())
        );
    }
}