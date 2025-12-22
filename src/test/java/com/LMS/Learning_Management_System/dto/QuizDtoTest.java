package com.LMS.Learning_Management_System.dto;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class QuizDtoTest {

    private QuizDto quizDto;
    private Date testDate;

    @BeforeEach
    void setUp() {
        quizDto = new QuizDto();
        testDate = new Date();
    }

    @Test
    void testDefaultConstructor() {
        QuizDto dto = new QuizDto();
        assertNotNull(dto);
        assertEquals(0, dto.getQuizId());
        assertNull(dto.getTitle());
        assertNull(dto.getCreation_date());
    }

    @Test
    void testParameterizedConstructor() {
        int expectedId = 1;
        String expectedTitle = "Java Quiz";
        Date expectedDate = new Date();

        QuizDto dto = new QuizDto(expectedId, expectedTitle, expectedDate);

        assertAll(
                () -> assertEquals(expectedId, dto.getQuizId()),
                () -> assertEquals(expectedTitle, dto.getTitle()),
                () -> assertEquals(expectedDate, dto.getCreation_date())
        );
    }

    @Test
    void testGetAndSetQuizId() {
        int expectedId = 10;
        quizDto.setQuizId(expectedId);
        assertEquals(expectedId, quizDto.getQuizId());
    }

    @Test
    void testGetAndSetTitle() {
        String expectedTitle = "Spring Boot Quiz";
        quizDto.setTitle(expectedTitle);
        assertEquals(expectedTitle, quizDto.getTitle());
    }

    @Test
    void testGetAndSetCreationDate() {
        quizDto.setCreation_date(testDate);
        assertEquals(testDate, quizDto.getCreation_date());
    }

    @Test
    void testGetAndSetType() {
        int expectedType = 2;
        quizDto.setType(expectedType);
        assertEquals(expectedType, quizDto.getType());
    }

    @Test
    void testGetAndSetQuestionList() {
        List<QuestionDto> questionList = new ArrayList<>();
        QuestionDto q1 = new QuestionDto();
        q1.setQuestion_id(1);
        q1.setQuestion_text("Question 1");
        questionList.add(q1);

        quizDto.setQuestionList(questionList);

        assertEquals(1, quizDto.getQuestionList().size());
        assertEquals("Question 1", quizDto.getQuestionList().get(0).getQuestion_text());
    }

    @Test
    void testGetAndSetCourseId() {
        int expectedCourseId = 303;
        quizDto.setCourse_id(expectedCourseId);
        assertEquals(expectedCourseId, quizDto.getCourse_id());
    }

    @Test
    void testQuestionListIsNullByDefault() {
        assertNull(quizDto.getQuestionList());
    }

    @Test
    void testEmptyQuestionList() {
        List<QuestionDto> emptyList = new ArrayList<>();
        quizDto.setQuestionList(emptyList);

        assertNotNull(quizDto.getQuestionList());
        assertTrue(quizDto.getQuestionList().isEmpty());
    }

    @Test
    void testCompleteQuizDto() {
        List<QuestionDto> questions = new ArrayList<>();
        QuestionDto q1 = new QuestionDto();
        q1.setQuestion_id(1);
        questions.add(q1);

        quizDto.setQuizId(5);
        quizDto.setTitle("Final Exam");
        quizDto.setCreation_date(testDate);
        quizDto.setType(1);
        quizDto.setQuestionList(questions);
        quizDto.setCourse_id(404);

        assertAll(
                () -> assertEquals(5, quizDto.getQuizId()),
                () -> assertEquals("Final Exam", quizDto.getTitle()),
                () -> assertEquals(testDate, quizDto.getCreation_date()),
                () -> assertEquals(1, quizDto.getType()),
                () -> assertEquals(1, quizDto.getQuestionList().size()),
                () -> assertEquals(404, quizDto.getCourse_id())
        );
    }
}