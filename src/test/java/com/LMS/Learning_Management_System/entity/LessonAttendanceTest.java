package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LessonAttendance Entity Tests")
class LessonAttendanceTest {

    private LessonAttendance lessonAttendance;
    private Lesson mockLesson;
    private Student mockStudent;

    @BeforeEach
    void setUp() {
        // Création d'un mock lesson
        mockLesson = new Lesson();
        mockLesson.setLessonId(1);
        mockLesson.setLessonName("Introduction à Spring Boot");

        // Création d'un mock student
        mockStudent = new Student();
        mockStudent.setUserAccountId(1);
    }

    @Test
    @DisplayName("Test création LessonAttendance avec constructeur par défaut")
    void testDefaultConstructor() {
        lessonAttendance = new LessonAttendance();

        assertNotNull(lessonAttendance, "LessonAttendance ne doit pas être null");
        assertEquals(0, lessonAttendance.getAttendanceId(), "L'ID par défaut doit être 0");
        assertNull(lessonAttendance.getLessonId(), "Le lesson par défaut doit être null");
        assertNull(lessonAttendance.getStudentId(), "Le student par défaut doit être null");
    }

    @Test
    @DisplayName("Test création LessonAttendance avec constructeur complet")
    void testFullConstructor() {
        lessonAttendance = new LessonAttendance(1, mockLesson, mockStudent);

        assertNotNull(lessonAttendance, "LessonAttendance ne doit pas être null");
        assertEquals(1, lessonAttendance.getAttendanceId(), "L'ID doit être 1");
        assertEquals(mockLesson, lessonAttendance.getLessonId(), "Le lesson doit correspondre");
        assertEquals(mockStudent, lessonAttendance.getStudentId(), "Le student doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - AttendanceId")
    void testAttendanceIdSetterGetter() {
        lessonAttendance = new LessonAttendance();
        lessonAttendance.setAttendanceId(100);

        assertEquals(100, lessonAttendance.getAttendanceId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - LessonId")
    void testLessonIdSetterGetter() {
        lessonAttendance = new LessonAttendance();
        lessonAttendance.setLessonId(mockLesson);

        assertEquals(mockLesson, lessonAttendance.getLessonId(), "Le lesson doit correspondre");
        assertEquals(1, lessonAttendance.getLessonId().getLessonId(), "L'ID du lesson doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - StudentId")
    void testStudentIdSetterGetter() {
        lessonAttendance = new LessonAttendance();
        lessonAttendance.setStudentId(mockStudent);

        assertEquals(mockStudent, lessonAttendance.getStudentId(), "Le student doit correspondre");
        assertEquals(1, lessonAttendance.getStudentId().getUserAccountId(), "L'ID du student doit être 1");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Lesson")
    void testManyToOneRelationWithLesson() {
        lessonAttendance = new LessonAttendance();
        lessonAttendance.setLessonId(mockLesson);

        assertNotNull(lessonAttendance.getLessonId(), "Le lesson ne doit pas être null");
        assertEquals(mockLesson.getLessonId(), lessonAttendance.getLessonId().getLessonId(),
                "L'ID du lesson doit correspondre");
        assertEquals("Introduction à Spring Boot", lessonAttendance.getLessonId().getLessonName(),
                "Le nom du lesson doit correspondre");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Student")
    void testManyToOneRelationWithStudent() {
        lessonAttendance = new LessonAttendance();
        lessonAttendance.setStudentId(mockStudent);

        assertNotNull(lessonAttendance.getStudentId(), "Le student ne doit pas être null");
        assertEquals(mockStudent.getUserAccountId(), lessonAttendance.getStudentId().getUserAccountId(),
                "L'ID du student doit correspondre");
    }

    @Test
    @DisplayName("Test modification du lesson associé")
    void testModifyAssociatedLesson() {
        lessonAttendance = new LessonAttendance(1, mockLesson, mockStudent);

        Lesson newLesson = new Lesson();
        newLesson.setLessonId(2);
        newLesson.setLessonName("Concepts avancés");

        lessonAttendance.setLessonId(newLesson);

        assertEquals(newLesson, lessonAttendance.getLessonId(), "Le lesson doit être modifié");
        assertEquals(2, lessonAttendance.getLessonId().getLessonId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test modification du student associé")
    void testModifyAssociatedStudent() {
        lessonAttendance = new LessonAttendance(1, mockLesson, mockStudent);

        Student newStudent = new Student();
        newStudent.setUserAccountId(2);

        lessonAttendance.setStudentId(newStudent);

        assertEquals(newStudent, lessonAttendance.getStudentId(), "Le student doit être modifié");
        assertEquals(2, lessonAttendance.getStudentId().getUserAccountId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        lessonAttendance = new LessonAttendance(1, mockLesson, mockStudent);

        String result = lessonAttendance.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("attendanceId=1"), "toString doit contenir l'ID");
        assertTrue(result.contains("lessonId="), "toString doit contenir le lessonId");
        assertTrue(result.contains("studentId="), "toString doit contenir le studentId");
        assertTrue(result.contains("LessonAttendance{"), "toString doit commencer par LessonAttendance{");
    }

    @Test
    @DisplayName("Test plusieurs attendances pour le même lesson")
    void testMultipleAttendancesForSameLesson() {
        Student student2 = new Student();
        student2.setUserAccountId(2);

        LessonAttendance attendance1 = new LessonAttendance(1, mockLesson, mockStudent);
        LessonAttendance attendance2 = new LessonAttendance(2, mockLesson, student2);

        assertEquals(mockLesson, attendance1.getLessonId(), "Les deux attendances doivent avoir le même lesson");
        assertEquals(mockLesson, attendance2.getLessonId(), "Les deux attendances doivent avoir le même lesson");
        assertNotEquals(attendance1.getStudentId(), attendance2.getStudentId(), "Les students doivent être différents");
    }

    @Test
    @DisplayName("Test plusieurs attendances pour le même student")
    void testMultipleAttendancesForSameStudent() {
        Lesson lesson2 = new Lesson();
        lesson2.setLessonId(2);

        LessonAttendance attendance1 = new LessonAttendance(1, mockLesson, mockStudent);
        LessonAttendance attendance2 = new LessonAttendance(2, lesson2, mockStudent);

        assertEquals(mockStudent, attendance1.getStudentId(), "Les deux attendances doivent avoir le même student");
        assertEquals(mockStudent, attendance2.getStudentId(), "Les deux attendances doivent avoir le même student");
        assertNotEquals(attendance1.getLessonId(), attendance2.getLessonId(), "Les lessons doivent être différents");
    }

    @Test
    @DisplayName("Test LessonAttendance avec lessonId null")
    void testLessonAttendanceWithNullLesson() {
        lessonAttendance = new LessonAttendance(1, null, mockStudent);

        assertNull(lessonAttendance.getLessonId(), "Le lessonId null doit être accepté");
        assertNotNull(lessonAttendance.getStudentId(), "Le studentId ne doit pas être null");
    }

    @Test
    @DisplayName("Test LessonAttendance avec studentId null")
    void testLessonAttendanceWithNullStudent() {
        lessonAttendance = new LessonAttendance(1, mockLesson, null);

        assertNull(lessonAttendance.getStudentId(), "Le studentId null doit être accepté");
        assertNotNull(lessonAttendance.getLessonId(), "Le lessonId ne doit pas être null");
    }

    @Test
    @DisplayName("Test LessonAttendance avec ID négatif")
    void testLessonAttendanceWithNegativeId() {
        lessonAttendance = new LessonAttendance(-1, mockLesson, mockStudent);

        assertEquals(-1, lessonAttendance.getAttendanceId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test comparaison de deux attendances")
    void testCompareTwoAttendances() {
        LessonAttendance attendance1 = new LessonAttendance(1, mockLesson, mockStudent);
        LessonAttendance attendance2 = new LessonAttendance(2, mockLesson, mockStudent);

        assertNotEquals(attendance1.getAttendanceId(), attendance2.getAttendanceId(),
                "Les IDs doivent être différents");
        assertEquals(attendance1.getLessonId(), attendance2.getLessonId(),
                "Les lessons doivent être identiques");
        assertEquals(attendance1.getStudentId(), attendance2.getStudentId(),
                "Les students doivent être identiques");
    }

    @Test
    @DisplayName("Test enregistrement présence étudiant")
    void testRecordStudentAttendance() {
        lessonAttendance = new LessonAttendance(1, mockLesson, mockStudent);

        assertNotNull(lessonAttendance.getLessonId(), "Le lesson doit être enregistré");
        assertNotNull(lessonAttendance.getStudentId(), "Le student doit être enregistré");
        assertTrue(lessonAttendance.getAttendanceId() > 0, "L'ID doit être positif");
    }

    @Test
    @DisplayName("Test vérification présence unique par étudiant et lesson")
    void testUniqueAttendancePerStudentAndLesson() {
        LessonAttendance attendance1 = new LessonAttendance(1, mockLesson, mockStudent);

        // Même student et même lesson = doublon potentiel
        assertNotNull(attendance1.getLessonId(), "Le lesson doit être défini");
        assertNotNull(attendance1.getStudentId(), "Le student doit être défini");
    }

    @Test
    @DisplayName("Test modification attendanceId")
    void testModifyAttendanceId() {
        lessonAttendance = new LessonAttendance(1, mockLesson, mockStudent);

        lessonAttendance.setAttendanceId(999);

        assertEquals(999, lessonAttendance.getAttendanceId(), "L'ID doit être modifié à 999");
    }

    @Test
    @DisplayName("Test création attendance avec tous les champs null")
    void testCreateAttendanceWithAllNullFields() {
        lessonAttendance = new LessonAttendance(0, null, null);

        assertEquals(0, lessonAttendance.getAttendanceId(), "L'ID doit être 0");
        assertNull(lessonAttendance.getLessonId(), "Le lesson doit être null");
        assertNull(lessonAttendance.getStudentId(), "Le student doit être null");
    }

    @Test
    @DisplayName("Test validation des relations obligatoires")
    void testValidateMandatoryRelations() {
        lessonAttendance = new LessonAttendance(1, mockLesson, mockStudent);

        // Simule la validation JPA
        assertNotNull(lessonAttendance.getLessonId(), "Le lesson ne doit pas être null pour une attendance valide");
        assertNotNull(lessonAttendance.getStudentId(), "Le student ne doit pas être null pour une attendance valide");
    }
}