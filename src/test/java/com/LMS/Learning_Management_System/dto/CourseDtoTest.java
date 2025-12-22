package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CourseDtoTest {

    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        courseDto = new CourseDto();
    }

    @Test
    void testDefaultConstructor() {
        CourseDto dto = new CourseDto();
        assertNotNull(dto);
    }

    @Test
    void testParameterizedConstructor() {
        CourseDto dto = new CourseDto(1, "Java Programming", "Learn Java", 40, "video.mp4", "John Doe");
        
        assertEquals(1, dto.getCourseId());
        assertEquals("Java Programming", dto.getCourseName());
        assertEquals("Learn Java", dto.getDescription());
        assertEquals(40, dto.getDuration());
        assertEquals("video.mp4", dto.getMedia());
        assertEquals("John Doe", dto.getInstructorName());
    }

    @Test
    void testGetAndSetCourseId() {
        int expectedId = 10;
        courseDto.setCourseId(expectedId);
        assertEquals(expectedId, courseDto.getCourseId());
    }

    @Test
    void testGetAndSetCourseName() {
        String expectedName = "Spring Boot Course";
        courseDto.setCourseName(expectedName);
        assertEquals(expectedName, courseDto.getCourseName());
    }

    @Test
    void testGetAndSetDescription() {
        String expectedDescription = "Advanced Spring Boot development";
        courseDto.setDescription(expectedDescription);
        assertEquals(expectedDescription, courseDto.getDescription());
    }

    @Test
    void testGetAndSetDuration() {
        int expectedDuration = 60;
        courseDto.setDuration(expectedDuration);
        assertEquals(expectedDuration, courseDto.getDuration());
    }

    @Test
    void testGetAndSetInstructorName() {
        String expectedInstructor = "Jane Smith";
        courseDto.setInstructorName(expectedInstructor);
        assertEquals(expectedInstructor, courseDto.getInstructorName());
    }

    @Test
    void testGetAndSetMedia() {
        String expectedMedia = "lecture.mp4";
        courseDto.setMedia(expectedMedia);
        assertEquals(expectedMedia, courseDto.getMedia());
    }

    @Test
    void testCourseDtoWithAllFields() {
        courseDto.setCourseId(25);
        courseDto.setCourseName("Database Design");
        courseDto.setDescription("Learn SQL and NoSQL");
        courseDto.setDuration(50);
        courseDto.setInstructorName("Alice Brown");
        courseDto.setMedia("db_tutorial.mp4");

        assertEquals(25, courseDto.getCourseId());
        assertEquals("Database Design", courseDto.getCourseName());
        assertEquals("Learn SQL and NoSQL", courseDto.getDescription());
        assertEquals(50, courseDto.getDuration());
        assertEquals("Alice Brown", courseDto.getInstructorName());
        assertEquals("db_tutorial.mp4", courseDto.getMedia());
    }

    @Test
    void testCourseDtoWithNullValues() {
        courseDto.setCourseName(null);
        courseDto.setDescription(null);
        courseDto.setInstructorName(null);
        courseDto.setMedia(null);

        assertNull(courseDto.getCourseName());
        assertNull(courseDto.getDescription());
        assertNull(courseDto.getInstructorName());
        assertNull(courseDto.getMedia());
    }

    @Test
    void testDurationWithZeroValue() {
        courseDto.setDuration(0);
        assertEquals(0, courseDto.getDuration());
    }

    @Test
    void testDurationWithNegativeValue() {
        courseDto.setDuration(-10);
        assertEquals(-10, courseDto.getDuration());
    }
}