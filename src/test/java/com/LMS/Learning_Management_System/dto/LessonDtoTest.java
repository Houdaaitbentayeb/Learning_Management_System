package com.LMS.Learning_Management_System.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LessonDtoTest {

    private LessonDto buildFull(int lessonId) {
        return new LessonDto(
                lessonId,
                10,
                "Introduction",
                "Course intro",
                1,
                "123456",
                "Content here",
                new Date(1700000000000L) // fixed date for stable equals/hashCode
        );
    }

    @Test
    void testDefaultConstructor() {
        LessonDto dto = new LessonDto();
        assertNotNull(dto);
    }

    @Test
    void testParameterizedConstructor_setsAllFields() {
        Date testDate = new Date(1700000000000L);
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
    void testGettersAndSetters_allFields() {
        LessonDto lessonDto = new LessonDto();
        Date testDate = new Date(1700000000000L);

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

    // ==========================
    // Lombok @Data coverage boost
    // ==========================

    @Test
    void testEqualsHashCode_sameValues() {
        LessonDto a = buildFull(1);
        LessonDto b = buildFull(1);

        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEquals_differentValues() {
        LessonDto a = buildFull(1);
        LessonDto b = buildFull(2);

        assertNotEquals(a, b);
        assertNotEquals(b, a);
    }

    @Test
    void testEquals_nullAndDifferentType() {
        LessonDto a = buildFull(1);

        assertNotEquals(null, a);
        assertNotEquals("not a LessonDto", a);
    }

    @Test
    void testEquals_withNullFields() {
        LessonDto a = new LessonDto();
        LessonDto b = new LessonDto();

        // both empty -> should be equal for Lombok-generated equals
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        // create mismatch with null/non-null
        a.setLessonName(null);
        b.setLessonName("X");
        assertNotEquals(a, b);

        // align again
        a.setLessonName("X");
        assertEquals(a, b);
    }

    @Test
    void testEquals_symmetryAndTransitivity() {
        LessonDto a = buildFull(1);
        LessonDto b = buildFull(1);
        LessonDto c = buildFull(1);

        // symmetry
        assertTrue(a.equals(b) && b.equals(a));

        // transitivity
        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }

    @Test
    void testToString_containsUsefulInfo() {
        LessonDto a = buildFull(1);

        String ts = a.toString();
        assertNotNull(ts);

        // depending on Lombok version/config, "LessonDto" or field names appear
        assertTrue(ts.contains("LessonDto") || ts.contains("lessonId") || ts.contains("lessonName"));
        assertTrue(ts.contains("Introduction") || ts.contains("123456") || ts.contains("Content here"));
    }

    @Test
    void testCanEqual_branch_ifGenerated() {
        // Some Lombok versions generate canEqual() in equals().
        // This test helps cover that branch by comparing 2 DTOs.
        LessonDto a = buildFull(1);
        LessonDto b = buildFull(1);

        assertEquals(a, b);
    }

    // ==========================
    // Edge cases (null/empty/negative)
    // ==========================

    @Test
    void testNullAndEmptyStrings() {
        LessonDto dto = new LessonDto();

        dto.setLessonName(null);
        dto.setLessonDescription(null);
        dto.setOTP(null);
        dto.setContent(null);

        assertNull(dto.getLessonName());
        assertNull(dto.getLessonDescription());
        assertNull(dto.getOTP());
        assertNull(dto.getContent());

        dto.setLessonName("");
        dto.setLessonDescription("");
        dto.setOTP("");
        dto.setContent("");

        assertEquals("", dto.getLessonName());
        assertEquals("", dto.getLessonDescription());
        assertEquals("", dto.getOTP());
        assertEquals("", dto.getContent());
    }

    @Test
    void testNumericFields_zeroAndNegative() {
        LessonDto dto = new LessonDto();

        dto.setLessonOrder(0);
        assertEquals(0, dto.getLessonOrder());

        dto.setLessonOrder(-1);
        assertEquals(-1, dto.getLessonOrder());

        dto.setLessonId(-5);
        assertEquals(-5, dto.getLessonId());

        dto.setCourseId(0);
        assertEquals(0, dto.getCourseId());
    }

    @Test
    void testCreationTime_multipleUpdatesAndNull() {
        LessonDto dto = new LessonDto();

        Date d1 = new Date(1690000000000L);
        Date d2 = new Date(1700000000000L);

        dto.setCreationTime(d1);
        assertEquals(d1, dto.getCreationTime());

        dto.setCreationTime(d2);
        assertEquals(d2, dto.getCreationTime());

        dto.setCreationTime(null);
        assertNull(dto.getCreationTime());
    }

    @Test
    void testContent_longText() {
        LessonDto dto = new LessonDto();
        String longContent = "This is a very long content ".repeat(200);

        dto.setContent(longContent);
        assertEquals(longContent, dto.getContent());
    }
}
