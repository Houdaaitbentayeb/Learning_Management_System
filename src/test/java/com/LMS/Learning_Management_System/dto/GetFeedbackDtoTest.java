package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class GetFeedbackDtoTest {

    private GetFeedbackDto getFeedbackDto;

    @BeforeEach
    void setUp() {
        getFeedbackDto = new GetFeedbackDto();
    }

    @Test
    void testGetAndSetAssignmentId() {
        int expectedId = 42;
        getFeedbackDto.setAssignmentId(expectedId);
        assertEquals(expectedId, getFeedbackDto.getAssignmentId());
    }

    @Test
    void testAssignmentIdWithZeroValue() {
        getFeedbackDto.setAssignmentId(0);
        assertEquals(0, getFeedbackDto.getAssignmentId());
    }

    @Test
    void testAssignmentIdWithNegativeValue() {
        getFeedbackDto.setAssignmentId(-5);
        assertEquals(-5, getFeedbackDto.getAssignmentId());
    }

    @Test
    void testAssignmentIdWithLargeValue() {
        int largeValue = Integer.MAX_VALUE;
        getFeedbackDto.setAssignmentId(largeValue);
        assertEquals(largeValue, getFeedbackDto.getAssignmentId());
    }

    @Test
    void testMultipleAssignments() {
        getFeedbackDto.setAssignmentId(10);
        assertEquals(10, getFeedbackDto.getAssignmentId());
        
        getFeedbackDto.setAssignmentId(20);
        assertEquals(20, getFeedbackDto.getAssignmentId());
        
        getFeedbackDto.setAssignmentId(30);
        assertEquals(30, getFeedbackDto.getAssignmentId());
    }

    @Test
    void testDefaultValue() {
        GetFeedbackDto newDto = new GetFeedbackDto();
        assertEquals(0, newDto.getAssignmentId());
    }

    @Test
    void testAssignmentIdWithMinValue() {
        int minValue = Integer.MIN_VALUE;
        getFeedbackDto.setAssignmentId(minValue);
        assertEquals(minValue, getFeedbackDto.getAssignmentId());
    }

    @Test
    void testAssignmentIdMultipleUpdates() {
        getFeedbackDto.setAssignmentId(100);
        assertEquals(100, getFeedbackDto.getAssignmentId());
        
        getFeedbackDto.setAssignmentId(200);
        assertEquals(200, getFeedbackDto.getAssignmentId());
        
        getFeedbackDto.setAssignmentId(300);
        assertEquals(300, getFeedbackDto.getAssignmentId());
    }
}