package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class LessonDtoTest {

    private LessonDto lessonDto;

    @BeforeEach
    void setUp() {
        lessonDto = new LessonDto();
    }

    @Test
    void testDefaultConstructor() {
        LessonDto dto = new LessonDto();
        assertNotNull(dto);
    }

    @Test
    void testParameterizedConstructor() {
        Date testDate = new Date();
        LessonDto dto = new LessonDto(1, 10, "Introduction", "Course intro", 1, "123456", "Content here", testDate);
        
        assertEquals(1, dto.getLessonId());
        assertEquals(10, dto.getCourseId());
        assertEquals("Introduction", dto.getLessonName());
        assertEquals("Course intro", dto.getLessonDescription());
        assertEquals(1, dto.getLessonOrder());
        assertEquals("123456", dto.getOTP());
        assertEquals("Content here", dto.getContent());
        assertEquals(testDate, dto.getCreationTime());
    }

    @Test
    void testGetAndSetLessonId() {
        int expectedId = 42;
        lessonDto.setLessonId(expectedId);
        assertEquals(expectedId, lessonDto.getLessonId());
    }

    @Test
    void testGetAndSetCourseId() {
        int expectedCourseId = 100;
        lessonDto.setCourseId(expectedCourseId);
        assertEquals(expectedCourseId, lessonDto.getCourseId());
    }

    @Test
    void testGetAndSetLessonName() {
        String expectedName = "Java Basics";
        lessonDto.setLessonName(expectedName);
        assertEquals(expectedName, lessonDto.getLessonName());
    }

    @Test
    void testGetAndSetLessonDescription() {
        String expectedDescription = "Learn the fundamentals of Java";
        lessonDto.setLessonDescription(expectedDescription);
        assertEquals(expectedDescription, lessonDto.getLessonDescription());
    }

    @Test
    void testGetAndSetLessonOrder() {
        int expectedOrder = 5;
        lessonDto.setLessonOrder(expectedOrder);
        assertEquals(expectedOrder, lessonDto.getLessonOrder());
    }

    @Test
    void testGetAndSetOTP() {
        String expectedOTP = "987654";
        lessonDto.setOTP(expectedOTP);
        assertEquals(expectedOTP, lessonDto.getOTP());
    }

    @Test
    void testGetAndSetContent() {
        String expectedContent = "This is the lesson content with detailed explanations";
        lessonDto.setContent(expectedContent);
        assertEquals(expectedContent, lessonDto.getContent());
    }

    @Test
    void testGetAndSetCreationTime() {
        Date expectedDate = new Date();
        lessonDto.setCreationTime(expectedDate);
        assertEquals(expectedDate, lessonDto.getCreationTime());
    }

    @Test
    void testLessonDtoWithAllFields() {
        Date testDate = new Date();
        lessonDto.setLessonId(15);
        lessonDto.setCourseId(200);
        lessonDto.setLessonName("Advanced Topics");
        lessonDto.setLessonDescription("Deep dive into advanced concepts");
        lessonDto.setLessonOrder(10);
        lessonDto.setOTP("ABC123");
        lessonDto.setContent("Comprehensive lesson content");
        lessonDto.setCreationTime(testDate);

        assertEquals(15, lessonDto.getLessonId());
        assertEquals(200, lessonDto.getCourseId());
        assertEquals("Advanced Topics", lessonDto.getLessonName());
        assertEquals("Deep dive into advanced concepts", lessonDto.getLessonDescription());
        assertEquals(10, lessonDto.getLessonOrder());
        assertEquals("ABC123", lessonDto.getOTP());
        assertEquals("Comprehensive lesson content", lessonDto.getContent());
        assertEquals(testDate, lessonDto.getCreationTime());
    }

    @Test
    void testLessonDtoWithNullStringValues() {
        lessonDto.setLessonName(null);
        lessonDto.setLessonDescription(null);
        lessonDto.setOTP(null);
        lessonDto.setContent(null);

        assertNull(lessonDto.getLessonName());
        assertNull(lessonDto.getLessonDescription());
        assertNull(lessonDto.getOTP());
        assertNull(lessonDto.getContent());
    }

    @Test
    void testLessonDtoWithEmptyStrings() {
        lessonDto.setLessonName("");
        lessonDto.setLessonDescription("");
        lessonDto.setOTP("");
        lessonDto.setContent("");

        assertEquals("", lessonDto.getLessonName());
        assertEquals("", lessonDto.getLessonDescription());
        assertEquals("", lessonDto.getOTP());
        assertEquals("", lessonDto.getContent());
    }

    @Test
    void testCreationTimeWithNullValue() {
        lessonDto.setCreationTime(null);
        assertNull(lessonDto.getCreationTime());
    }

    @Test
    void testLessonOrderWithZeroValue() {
        lessonDto.setLessonOrder(0);
        assertEquals(0, lessonDto.getLessonOrder());
    }

    @Test
    void testLessonOrderWithNegativeValue() {
        lessonDto.setLessonOrder(-1);
        assertEquals(-1, lessonDto.getLessonOrder());
    }

    @Test
    void testLessonIdWithNegativeValue() {
        lessonDto.setLessonId(-5);
        assertEquals(-5, lessonDto.getLessonId());
    }

    @Test
    void testCourseIdWithZeroValue() {
        lessonDto.setCourseId(0);
        assertEquals(0, lessonDto.getCourseId());
    }

    @Test
    void testOTPWithNumericString() {
        String numericOTP = "123456";
        lessonDto.setOTP(numericOTP);
        assertEquals(numericOTP, lessonDto.getOTP());
    }

    @Test
    void testOTPWithAlphanumericString() {
        String alphanumericOTP = "ABC123XYZ";
        lessonDto.setOTP(alphanumericOTP);
        assertEquals(alphanumericOTP, lessonDto.getOTP());
    }

    @Test
    void testContentWithLongText() {
        String longContent = "This is a very long content ".repeat(100);
        lessonDto.setContent(longContent);
        assertEquals(longContent, lessonDto.getContent());
    }

    @Test
    void testMultipleDateUpdates() {
        Date date1 = new Date(System.currentTimeMillis() - 10000);
        Date date2 = new Date();
        
        lessonDto.setCreationTime(date1);
        assertEquals(date1, lessonDto.getCreationTime());
        
        lessonDto.setCreationTime(date2);
        assertEquals(date2, lessonDto.getCreationTime());
    }
}