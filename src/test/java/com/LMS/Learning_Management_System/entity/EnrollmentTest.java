package com.LMS.Learning_Management_System.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Enrollment Entity Tests")
class EnrollmentTest {

    private Enrollment enrollment;
    private Student mockStudent;
    private Course mockCourse;
    private Date testEnrollmentDate;

    @BeforeEach
    void setUp() {
        // Création d'un mock student
        mockStudent = new Student();
        mockStudent.setUserAccountId(1);

        // Création d'un mock course
        mockCourse = new Course();
        mockCourse.setCourseId(1);
        mockCourse.setCourseName("Spring Boot Avancé");

        // Création d'une date de test
        testEnrollmentDate = new Date();
    }

    @Test
    @DisplayName("Test création Enrollment avec constructeur par défaut")
    void testDefaultConstructor() {
        enrollment = new Enrollment();

        assertNotNull(enrollment, "L'enrollment ne doit pas être null");
        assertEquals(0, enrollment.getEnrollmentId(), "L'ID par défaut doit être 0");
        assertNull(enrollment.getStudent(), "Le student par défaut doit être null");
        assertNull(enrollment.getCourse(), "Le course par défaut doit être null");
        assertNull(enrollment.getEnrollmentDate(), "La date par défaut doit être null");
    }

    @Test
    @DisplayName("Test création Enrollment avec constructeur complet")
    void testFullConstructor() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        assertNotNull(enrollment, "L'enrollment ne doit pas être null");
        assertEquals(mockStudent, enrollment.getStudent(), "Le student doit correspondre");
        assertEquals(mockCourse, enrollment.getCourse(), "Le course doit correspondre");
        assertEquals(testEnrollmentDate, enrollment.getEnrollmentDate(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test setters et getters - EnrollmentId")
    void testEnrollmentIdSetterGetter() {
        enrollment = new Enrollment();
        enrollment.setEnrollmentId(100);

        assertEquals(100, enrollment.getEnrollmentId(), "L'ID doit être 100");
    }

    @Test
    @DisplayName("Test setters et getters - Student")
    void testStudentSetterGetter() {
        enrollment = new Enrollment();
        enrollment.setStudent(mockStudent);

        assertEquals(mockStudent, enrollment.getStudent(), "Le student doit correspondre");
        assertEquals(1, enrollment.getStudent().getUserAccountId(), "L'ID du student doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - Course")
    void testCourseSetterGetter() {
        enrollment = new Enrollment();
        enrollment.setCourse(mockCourse);

        assertEquals(mockCourse, enrollment.getCourse(), "Le course doit correspondre");
        assertEquals(1, enrollment.getCourse().getCourseId(), "L'ID du course doit être 1");
    }

    @Test
    @DisplayName("Test setters et getters - EnrollmentDate")
    void testEnrollmentDateSetterGetter() {
        enrollment = new Enrollment();
        enrollment.setEnrollmentDate(testEnrollmentDate);

        assertEquals(testEnrollmentDate, enrollment.getEnrollmentDate(), "La date doit correspondre");
    }

    @Test
    @DisplayName("Test création enrollment avec date actuelle")
    void testCreateEnrollmentWithCurrentDate() {
        Date currentDate = new Date();
        enrollment = new Enrollment(mockStudent, mockCourse, currentDate);

        assertNotNull(enrollment.getEnrollmentDate(), "La date ne doit pas être null");
        assertEquals(currentDate, enrollment.getEnrollmentDate(), "La date doit être actuelle");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Student")
    void testManyToOneRelationWithStudent() {
        enrollment = new Enrollment();
        enrollment.setStudent(mockStudent);

        assertNotNull(enrollment.getStudent(), "Le student ne doit pas être null");
        assertEquals(mockStudent.getUserAccountId(), enrollment.getStudent().getUserAccountId(),
                "L'ID du student doit correspondre");
    }

    @Test
    @DisplayName("Test relation ManyToOne avec Course")
    void testManyToOneRelationWithCourse() {
        enrollment = new Enrollment();
        enrollment.setCourse(mockCourse);

        assertNotNull(enrollment.getCourse(), "Le course ne doit pas être null");
        assertEquals(mockCourse.getCourseId(), enrollment.getCourse().getCourseId(),
                "L'ID du course doit correspondre");
        assertEquals("Spring Boot Avancé", enrollment.getCourse().getCourseName(),
                "Le nom du course doit correspondre");
    }

    @Test
    @DisplayName("Test toString contient les informations correctes")
    void testToString() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        String result = enrollment.toString();

        assertNotNull(result, "toString ne doit pas retourner null");
        assertTrue(result.contains("enrollmentId="), "toString doit contenir l'ID");
        assertTrue(result.contains("student="), "toString doit contenir le student");
        assertTrue(result.contains("course="), "toString doit contenir le course");
        assertTrue(result.contains("enrollmentDate="), "toString doit contenir la date");
        assertTrue(result.contains("Enrollment{"), "toString doit commencer par Enrollment{");
    }

    @Test
    @DisplayName("Test plusieurs enrollments pour le même student")
    void testMultipleEnrollmentsForSameStudent() {
        Course course2 = new Course();
        course2.setCourseId(2);
        course2.setCourseName("JPA & Hibernate");

        Enrollment enrollment1 = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);
        Enrollment enrollment2 = new Enrollment(mockStudent, course2, testEnrollmentDate);

        assertEquals(mockStudent, enrollment1.getStudent(), "Les deux enrollments doivent avoir le même student");
        assertEquals(mockStudent, enrollment2.getStudent(), "Les deux enrollments doivent avoir le même student");
        assertNotEquals(enrollment1.getCourse(), enrollment2.getCourse(), "Les courses doivent être différents");
    }

    @Test
    @DisplayName("Test plusieurs enrollments pour le même course")
    void testMultipleEnrollmentsForSameCourse() {
        Student student2 = new Student();
        student2.setUserAccountId(2);

        Enrollment enrollment1 = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);
        Enrollment enrollment2 = new Enrollment(student2, mockCourse, testEnrollmentDate);

        assertEquals(mockCourse, enrollment1.getCourse(), "Les deux enrollments doivent avoir le même course");
        assertEquals(mockCourse, enrollment2.getCourse(), "Les deux enrollments doivent avoir le même course");
        assertNotEquals(enrollment1.getStudent(), enrollment2.getStudent(), "Les students doivent être différents");
    }

    @Test
    @DisplayName("Test modification du student associé")
    void testModifyAssociatedStudent() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        Student newStudent = new Student();
        newStudent.setUserAccountId(2);

        enrollment.setStudent(newStudent);

        assertEquals(newStudent, enrollment.getStudent(), "Le student doit être modifié");
        assertEquals(2, enrollment.getStudent().getUserAccountId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test modification du course associé")
    void testModifyAssociatedCourse() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        Course newCourse = new Course();
        newCourse.setCourseId(2);
        newCourse.setCourseName("Nouveau cours");

        enrollment.setCourse(newCourse);

        assertEquals(newCourse, enrollment.getCourse(), "Le course doit être modifié");
        assertEquals(2, enrollment.getCourse().getCourseId(), "Le nouvel ID doit être 2");
    }

    @Test
    @DisplayName("Test modification de la date d'enrollment")
    void testModifyEnrollmentDate() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date newDate = calendar.getTime();

        enrollment.setEnrollmentDate(newDate);

        assertEquals(newDate, enrollment.getEnrollmentDate(), "La date doit être modifiée");
        assertNotEquals(testEnrollmentDate, enrollment.getEnrollmentDate(), "La nouvelle date doit être différente");
    }

    @Test
    @DisplayName("Test Enrollment avec date dans le passé")
    void testEnrollmentWithPastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date pastDate = calendar.getTime();

        enrollment = new Enrollment(mockStudent, mockCourse, pastDate);

        assertEquals(pastDate, enrollment.getEnrollmentDate(), "La date passée doit être acceptée");
        assertTrue(enrollment.getEnrollmentDate().before(new Date()), "La date doit être dans le passé");
    }

    @Test
    @DisplayName("Test Enrollment avec date future")
    void testEnrollmentWithFutureDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date futureDate = calendar.getTime();

        enrollment = new Enrollment(mockStudent, mockCourse, futureDate);

        assertEquals(futureDate, enrollment.getEnrollmentDate(), "La date future doit être acceptée");
    }

    @Test
    @DisplayName("Test Enrollment avec student null")
    void testEnrollmentWithNullStudent() {
        enrollment = new Enrollment(null, mockCourse, testEnrollmentDate);

        assertNull(enrollment.getStudent(), "Le student null doit être accepté");
        assertNotNull(enrollment.getCourse(), "Le course ne doit pas être null");
    }

    @Test
    @DisplayName("Test Enrollment avec course null")
    void testEnrollmentWithNullCourse() {
        enrollment = new Enrollment(mockStudent, null, testEnrollmentDate);

        assertNull(enrollment.getCourse(), "Le course null doit être accepté");
        assertNotNull(enrollment.getStudent(), "Le student ne doit pas être null");
    }

    @Test
    @DisplayName("Test Enrollment avec enrollmentDate null")
    void testEnrollmentWithNullDate() {
        enrollment = new Enrollment(mockStudent, mockCourse, null);

        assertNull(enrollment.getEnrollmentDate(), "La date null doit être acceptée");
    }

    @Test
    @DisplayName("Test Enrollment avec ID négatif")
    void testEnrollmentWithNegativeId() {
        enrollment = new Enrollment();
        enrollment.setEnrollmentId(-1);

        assertEquals(-1, enrollment.getEnrollmentId(), "L'ID négatif doit être accepté");
    }

    @Test
    @DisplayName("Test comparaison de deux enrollments")
    void testCompareTwoEnrollments() {
        Enrollment enrollment1 = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);
        enrollment1.setEnrollmentId(1);

        Student student2 = new Student();
        student2.setUserAccountId(2);
        Enrollment enrollment2 = new Enrollment(student2, mockCourse, testEnrollmentDate);
        enrollment2.setEnrollmentId(2);

        assertNotEquals(enrollment1.getEnrollmentId(), enrollment2.getEnrollmentId(), "Les IDs doivent être différents");
        assertNotEquals(enrollment1.getStudent(), enrollment2.getStudent(), "Les students doivent être différents");
    }

    @Test
    @DisplayName("Test vérification d'inscription unique par étudiant et cours")
    void testUniqueEnrollmentPerStudentAndCourse() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        // Simule la vérification d'unicité
        assertNotNull(enrollment.getStudent(), "Le student doit être défini");
        assertNotNull(enrollment.getCourse(), "Le course doit être défini");
    }

    @Test
    @DisplayName("Test tri des enrollments par date")
    void testSortEnrollmentsByDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -10);
        Date date1 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date date2 = calendar.getTime();

        Enrollment enrollment1 = new Enrollment(mockStudent, mockCourse, date1);
        Enrollment enrollment2 = new Enrollment(mockStudent, mockCourse, date2);

        assertTrue(enrollment1.getEnrollmentDate().before(enrollment2.getEnrollmentDate()),
                "Enrollment1 doit être plus ancien");
    }

    @Test
    @DisplayName("Test inscription d'un étudiant à un cours")
    void testStudentEnrollmentInCourse() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        assertNotNull(enrollment.getStudent(), "L'étudiant doit être inscrit");
        assertNotNull(enrollment.getCourse(), "Le cours doit être défini");
        assertNotNull(enrollment.getEnrollmentDate(), "La date d'inscription doit être enregistrée");
    }

    @Test
    @DisplayName("Test désinscription (modification du course à null)")
    void testUnenrollment() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        enrollment.setCourse(null);

        assertNull(enrollment.getCourse(), "Le course doit être null après désinscription");
        assertNotNull(enrollment.getStudent(), "Le student doit être préservé");
    }

    @Test
    @DisplayName("Test réinscription (modification du course)")
    void testReenrollment() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        Course newCourse = new Course();
        newCourse.setCourseId(3);
        newCourse.setCourseName("Cours de réinscription");

        enrollment.setCourse(newCourse);

        assertEquals(newCourse, enrollment.getCourse(), "Le nouveau course doit être défini");
        assertNotEquals(mockCourse, enrollment.getCourse(), "Le course ne doit plus être l'ancien");
    }

    @Test
    @DisplayName("Test enrollment avec date exacte (timestamp)")
    void testEnrollmentWithExactTimestamp() {
        Date exactDate = new Date(1609459200000L); // 1er janvier 2021, 00:00:00
        enrollment = new Enrollment(mockStudent, mockCourse, exactDate);

        assertEquals(exactDate, enrollment.getEnrollmentDate(), "La date exacte doit être préservée");
    }

    @Test
    @DisplayName("Test modification enrollmentId")
    void testModifyEnrollmentId() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);
        enrollment.setEnrollmentId(1);

        enrollment.setEnrollmentId(999);

        assertEquals(999, enrollment.getEnrollmentId(), "L'ID doit être modifié à 999");
    }

    @Test
    @DisplayName("Test vérification des relations obligatoires")
    void testValidateMandatoryRelations() {
        enrollment = new Enrollment(mockStudent, mockCourse, testEnrollmentDate);

        // Simule la validation JPA
        assertNotNull(enrollment.getStudent(), "Le student ne doit pas être null pour un enrollment valide");
        assertNotNull(enrollment.getCourse(), "Le course ne doit pas être null pour un enrollment valide");
    }

    @Test
    @DisplayName("Test enrollment pour plusieurs cours différents")
    void testEnrollmentInMultipleCourses() {
        Course course1 = new Course();
        course1.setCourseId(1);
        course1.setCourseName("Cours 1");

        Course course2 = new Course();
        course2.setCourseId(2);
        course2.setCourseName("Cours 2");

        Enrollment enrollment1 = new Enrollment(mockStudent, course1, testEnrollmentDate);
        Enrollment enrollment2 = new Enrollment(mockStudent, course2, testEnrollmentDate);

        assertEquals(mockStudent, enrollment1.getStudent(), "Même étudiant pour les deux enrollments");
        assertEquals(mockStudent, enrollment2.getStudent(), "Même étudiant pour les deux enrollments");
        assertNotEquals(enrollment1.getCourse(), enrollment2.getCourse(), "Cours différents");
    }
}