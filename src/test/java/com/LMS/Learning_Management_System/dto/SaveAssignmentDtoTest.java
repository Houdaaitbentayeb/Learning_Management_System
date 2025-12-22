package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class SaveAssignmentDtoTest {

    private SaveAssignmentDto saveAssignmentDto;

    @BeforeEach
    void setUp() {
        saveAssignmentDto = new SaveAssignmentDto();
    }

    @Test
    void testGetAndSetStudentId() {
        int expectedId = 123;
        saveAssignmentDto.setStudentId(expectedId);
        assertEquals(expectedId, saveAssignmentDto.getStudentId());
    }

    @Test
    void testGetAndSetAssignmentId() {
        int expectedId = 456;
        saveAssignmentDto.setAssignmentId(expectedId);
        assertEquals(expectedId, saveAssignmentDto.getAssignmentId());
    }

    @Test
    void testGetAndSetFeedback() {
        String expectedFeedback = "Great work! Well done.";
        saveAssignmentDto.setFeedback(expectedFeedback);
        assertEquals(expectedFeedback, saveAssignmentDto.getFeedback());
    }

    @Test
    void testDefaultValues() {
        assertEquals(0, saveAssignmentDto.getStudentId());
        assertEquals(0, saveAssignmentDto.getAssignmentId());
        assertNull(saveAssignmentDto.getFeedback());
    }

    @Test
    void testSetFeedbackWithNull() {
        saveAssignmentDto.setFeedback(null);
        assertNull(saveAssignmentDto.getFeedback());
    }

    @Test
    void testSetFeedbackWithEmptyString() {
        String emptyFeedback = "";
        saveAssignmentDto.setFeedback(emptyFeedback);
        assertEquals(emptyFeedback, saveAssignmentDto.getFeedback());
    }

    @Test
    void testCompleteSaveAssignmentDto() {
        saveAssignmentDto.setStudentId(789);
        saveAssignmentDto.setAssignmentId(321);
        saveAssignmentDto.setFeedback("Needs improvement on section 2");

        assertAll(
                () -> assertEquals(789, saveAssignmentDto.getStudentId()),
                () -> assertEquals(321, saveAssignmentDto.getAssignmentId()),
                () -> assertEquals("Needs improvement on section 2", saveAssignmentDto.getFeedback())
        );
    }

    @Test
    void testLongFeedback() {
        String longFeedback = "This is a very detailed feedback that includes multiple points about the assignment. " +
                "The student showed excellent understanding of the core concepts. " +
                "However, there are areas for improvement in the implementation.";
        saveAssignmentDto.setFeedback(longFeedback);
        assertEquals(longFeedback, saveAssignmentDto.getFeedback());
    }
}