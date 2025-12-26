package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.dto.CourseDto;
import com.LMS.Learning_Management_System.dto.GradingDto;
import com.LMS.Learning_Management_System.dto.QuestionDto;
import com.LMS.Learning_Management_System.dto.QuizDto;
import com.LMS.Learning_Management_System.entity.*;
import com.LMS.Learning_Management_System.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.LMS.Learning_Management_System.dto.StudentDto;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class QuizServiceTest {
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private GradingRepository gradingRepository;
    @Mock
    private HttpServletRequest request;
    @Mock
    private SessionAuthService sessionAuthService;
    @Mock private QuestionTypeRepository questionTypeRepository;
    @Mock private NotificationsService notificationsService;
    @Mock private EnrollmentService enrollmentService;
    @Mock private ObjectMapper objectMapper;


    @Mock
    EnrollmentRepository enrollmentRepository;
    @InjectMocks
    private CourseService courseService;
    @InjectMocks
    private QuizService quizService;

    private Users instructorUser;
    private Users studentUser;
    private Course course;
    private Enrollment enrollment;
    private UsersType instructorType;
    private UsersType studentType;
    private Quiz quiz;
    private Grading grading;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        instructorType = new UsersType();
        instructorType.setUserTypeId(3);

        studentType = new UsersType();
        studentType.setUserTypeId(2);

        instructorUser = new Users();
        instructorUser.setUserId(1);
        instructorUser.setUserTypeId(instructorType);

        studentUser = new Users();
        studentUser.setUserId(2);
        studentUser.setUserTypeId(studentType);

        course = new Course();
        course.setCourseId(1);
        course.setCourseName("Java Basics");
        course.setInstructorId(new Instructor());
        course.getInstructorId().setUserAccountId(1);

        enrollment = new Enrollment();
        enrollment.setStudent(new Student());
        enrollment.getStudent().setUserAccountId(2);
        enrollment.setCourse(course);
        enrollment.setEnrollmentId(1);

        Student student = new Student();
        student.setUserAccountId(2);
        student.setUserId(studentUser);

        quiz = new Quiz();
        quiz.setQuizId(1);
        quiz.setTitle("Test Quiz");
        quiz.setCourse(course);

        grading = new Grading();
        grading.setGradingId(1);
        grading.setQuiz_id(quiz);
        grading.setStudent_id(student);
        grading.setGrade(75);
    }

    @Test
    void testCreateQuiz_nonInstructor() {
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizService.Create(1, 1, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }
    @Test
    void testCreateQuiz_notCourseInstructor() {

        Users inValidInstructorUser = new Users();
        inValidInstructorUser.setUserId(3);
        inValidInstructorUser.setUserTypeId(instructorType);

        when(sessionAuthService.requireUser(request)).thenReturn(inValidInstructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizService.Create(1, 1, request);
        });

        assertEquals("Logged-in instructor does not have access for this course.", exception.getMessage());
    }
    @Test
    void testCreateQuiz_success() throws Exception {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        // 5 questions dispo + 5 questions "empty"
        List<Question> all = new ArrayList<>();
        List<Question> empty = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            all.add(new Question());
            empty.add(new Question());
        }

        when(questionRepository.findQuestionsByCourseIdAndQuestionType(1, 1)).thenReturn(all);
        when(questionRepository.findEmptyQuestionsByCourseIdAndQuestionType(1, 1)).thenReturn(empty);

        when(quizRepository.findAll()).thenReturn(new ArrayList<>());

        // enrolled students list for notifications
        StudentDto s = new StudentDto(2, "A", "B");
        when(enrollmentService.viewEnrolledStudents(1, request)).thenReturn(List.of(s));

        // IMPORTANT: simuler que save() retourne un quiz avec un id (sinon quizId = 0)
        when(quizRepository.save(any(Quiz.class))).thenAnswer(invocation -> {
            Quiz saved = invocation.getArgument(0);
            saved.setQuizId(123); // fake generated ID
            return saved;
        });

        // Act
        int createdQuizId = quizService.Create(1, 1, request);

        // Assert
        assertEquals(123, createdQuizId);

        verify(quizRepository, times(1)).save(any(Quiz.class));
        verify(notificationsService, times(1))
                .sendNotification(contains("A new Quiz with id: 123"), eq(2));
    }
    @Test
    void testGetActiveQuiz_hasActiveQuiz() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        Student student = new Student();
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        Quiz q = new Quiz();
        q.setQuizId(10);
        q.setCourse(course);
        q.setCreationDate(new Date()); // maintenant => actif

        when(quizRepository.getQuizzesByCourseId(1)).thenReturn(List.of(q));

        String result = quizService.getActiveQuiz(1, request);

        assertTrue(result.contains("quiz with id: 10"));
        assertTrue(result.contains("has time left"));
    }
    @Test
    void testGetQuizQuestions_success_student() throws Exception {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        Quiz q = new Quiz();
        q.setQuizId(1);
        q.setCourse(course);
        q.setCreationDate(new Date()); // actif

        when(quizRepository.findById(1)).thenReturn(Optional.of(q));

        Student student = new Student();
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        when(gradingRepository.boolFindGradeByQuizAndStudentID(1, 2)).thenReturn(Optional.of(false));

        QuestionType qt = new QuestionType();
        qt.setTypeId(1);

        Question question = new Question();
        question.setQuestionId(99);
        question.setQuestionText("Q?");
        question.setCorrectAnswer("A");
        question.setCourseId(course);
        question.setQuestionType(qt);
        question.setOptions("[]");

        when(questionRepository.findQuestionsByQuizId(1)).thenReturn(List.of(question));

        List<QuestionDto> result = quizService.getQuizQuestions(1, request);

        assertEquals(1, result.size());
        assertEquals(99, result.get(0).getQuestion_id());
        assertEquals("Q?", result.get(0).getQuestion_text());
    }
    @Test
    void testGradeQuiz_success_student() throws Exception {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        Quiz q = new Quiz();
        q.setQuizId(1);
        q.setCourse(course);
        q.setCreationDate(new Date()); // actif

        when(quizRepository.findById(1)).thenReturn(Optional.of(q));

        Student student = new Student();
        student.setUserAccountId(2);
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));

        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);
        when(gradingRepository.boolFindGradeByQuizAndStudentID(1, 2)).thenReturn(Optional.of(false));

        // 2 questions : 1 bon, 1 faux
        Question q1 = new Question(); q1.setCorrectAnswer("A");
        Question q2 = new Question(); q2.setCorrectAnswer("B");
        when(questionRepository.findQuestionsByQuizId(1)).thenReturn(List.of(q1, q2));

        GradingDto dto = new GradingDto();
        dto.setQuiz_id(1);
        dto.setAnswers(List.of("A", "X"));

        quizService.gradeQuiz(dto, request);

        verify(gradingRepository, times(1)).save(any(Grading.class));
        verify(notificationsService, times(1)).sendNotification(contains("has been graded"), eq(2));
    }
    @Test
    void testQuizFeedback_success() throws Exception {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        Quiz q = new Quiz();
        q.setQuizId(1);
        q.setCourse(course);

        when(quizRepository.findById(1)).thenReturn(Optional.of(q));
        when(courseRepository.findByInstructorId(1, 1)).thenReturn(true);

        when(gradingRepository.findGradeByQuizAndStudentID(1, 2)).thenReturn(4);

        int grade = quizService.quizFeedback(1, 2, request);

        assertEquals(4, grade);
    }
    @Test
    void testCreateQuiz_noQuestions() {

        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));


        when(questionRepository
                .findQuestionsByCourseIdAndQuestionType(course.getCourseId(),1)).thenReturn(new ArrayList<Question>());

        Exception exception = assertThrows(Exception.class, () -> {
            quizService.Create(1, 1, request);
        });

        assertEquals("No enough Questions to create quiz!\n", exception.getMessage());
    }
    @Test
    void testCreateQuiz_noUnassignedQuestions() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        List<Question> questions = new ArrayList<Question>();
        for (int i = 0; i < 5; i++) {
            questions.add(new Question());
        }
        when(questionRepository
                .findQuestionsByCourseIdAndQuestionType(course.getCourseId(),1)).thenReturn(questions);
        when(questionRepository
                .findEmptyQuestionsByCourseIdAndQuestionType(course.getCourseId(),1)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(Exception.class, () -> {
            quizService.Create(1, 1, request);
        });

        assertEquals("No enough unassigned questions to create new quiz! number: "+0+" type "+1+"\n", exception.getMessage());
    }
    @Test
    void testGetQuizById_inValidQuiz() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizService.getQuizByID(1, request);
        });

        assertEquals("No quiz found with the given ID: 1", exception.getMessage());
    }
    @Test
    void testGetActiveQuiz_notEnrolled() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(studentRepository.findById(2)).thenReturn(Optional.of(new Student()));

//        when(enrollmentRepository.existsByStudentAndCourse(new Student(),course)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizService.getActiveQuiz(1, request);
        });

        assertEquals("You are not enrolled this course.", exception.getMessage());
    }
    @Test
    void testGetActiveQuiz_noQuizzes() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        Student student = new Student();
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));

        when(enrollmentRepository.existsByStudentAndCourse(student,course)).thenReturn(true);
        when(quizRepository.getQuizzesByCourseId(1)).thenReturn(new ArrayList<Quiz>());

        assertEquals("No Current Quizzes\n overall Quizzes: 0",quizService.getActiveQuiz(1, request));
    }
    @Test
    void testGetQuizQuestions_lateEnter() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);
        // Milliseconds for 20th December 2024
        long millis = 1734633600000L;
        Date date = new Date();
        date.setTime(millis);
        quiz = new Quiz();
        quiz.setCreationDate(date);
        quiz.setCourse(course);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(courseRepository.findByInstructorId(studentUser.getUserId(),quiz.getCourse().getCourseId())).thenReturn(false);

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        Student student = new Student();
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));

        when(enrollmentRepository.existsByStudentAndCourse(student,course)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizService.getQuizQuestions(1, request);
        });

        assertEquals("The quiz has been finished!",exception.getMessage());


    }
    @Test
    void testGetQuizQuestions_reSubmit() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        Date date = new Date();
        quiz = new Quiz();
        quiz.setCreationDate(date);
        quiz.setCourse(course);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(courseRepository.findByInstructorId(studentUser.getUserId(),quiz.getCourse().getCourseId())).thenReturn(false);

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        Student student = new Student();
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));

        when(enrollmentRepository.existsByStudentAndCourse(student,course)).thenReturn(true);
        when(gradingRepository.boolFindGradeByQuizAndStudentID(quiz.getQuizId(),studentUser.getUserId()))
                .thenReturn(Optional.of(true));

        Exception exception = assertThrows(Exception.class, () -> {
            quizService.getQuizQuestions(1, request);
        });

        assertEquals("You have submitted a response earlier!",exception.getMessage());


    }
    @Test
    void testAddQuestion_studentAccess() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        when(courseRepository.findByInstructorId(2,1)).thenReturn(false);
        QuestionDto questionDto = new QuestionDto();
        questionDto.setCourse_id(1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quizService.addQuestion(questionDto, request);
        });

        assertEquals("You don't have permission to use this feature.",exception.getMessage());


    }
    @Test
    void testAddQuestion_duplicatedQuestion() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        when(courseRepository.findByInstructorId(1,1)).thenReturn(true);
        when(questionRepository.findById(1)).thenReturn(Optional.of(new Question()));

        QuestionDto questionDto = new QuestionDto();
        questionDto.setCourse_id(1);
        questionDto.setQuestion_id(1);
        Exception exception = assertThrows(Exception.class, () -> {
            quizService.addQuestion(questionDto, request);
        });

        assertEquals("question already exists",exception.getMessage());


    }
    @Test
    void testCreateQuestionBank_nonUser() {
        when(sessionAuthService.requireUser(request)).thenReturn(null);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(Exception.class, () -> {
            quizService.createQuestionBank(1,new ArrayList<QuestionDto>(), request);
        });

        assertEquals("No user is logged in.",exception.getMessage());


    }
    @Test
    void testGetQuestionBank_doesntExist() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        when(courseRepository.findByInstructorId(1,1))
                .thenReturn(true);
        when(questionRepository.findQuestionsByCourseId(1)).thenReturn(new ArrayList<Question>());


        Exception exception = assertThrows(Exception.class, () -> {
            quizService.getQuestionBank(1, request);
        });

        assertEquals("this course doesn't have any!",exception.getMessage());


    }
    @Test
    void testGradeQuiz_Instructor() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        Student student = new Student();
        Quiz quiz1 = new Quiz();
        quiz1.setCourse(course);

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz1));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(enrollmentRepository.existsByStudentAndCourse(student,course)).thenReturn(true);

        GradingDto gradingDto = new GradingDto();
        gradingDto.setStudent_id(2);
        gradingDto.setQuiz_id(1);
        Exception exception = assertThrows(Exception.class, () -> {
            quizService.gradeQuiz(gradingDto, request);
        });

        assertEquals("You are not authorized to submit quizzes! ",exception.getMessage());


    }
    @Test
    void testQuizFeedback_anotherStudent() {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Student student = new Student();
        Quiz quiz1 = new Quiz();
        quiz1.setCourse(course);

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz1));
        when(courseRepository.findByInstructorId(2,1)).thenReturn(false);
        when(studentRepository.findById(2)).thenReturn(Optional.of(student));

        when(enrollmentRepository.existsByStudentAndCourse(student,course)).thenReturn(true);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));


        Exception exception = assertThrows(Exception.class, () -> {
            quizService.quizFeedback(1,5, request);
        });

        assertEquals("You are not authorized to check other student's grades!",exception.getMessage());


    }
    @Test
    void testQuizFeedback_notGraded() {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Student student = new Student();
        Quiz quiz1 = new Quiz();
        quiz1.setCourse(course);

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz1));
        when(courseRepository.findByInstructorId(1,1)).thenReturn(true);
        when(gradingRepository.findGradeByQuizAndStudentID(1,5)).thenReturn(-1);

        Exception exception = assertThrows(Exception.class, () -> {
            quizService.quizFeedback(1,5, request);
        });

        assertEquals("Quiz haven't been graded yet",exception.getMessage());


    }

    @Test
    void testQuizGrades_noLoggedInUser()
    {
        when(sessionAuthService.requireUser(request)).thenReturn(null);
        when(quizRepository.existsById(1)).thenReturn(true);
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        when(gradingRepository.findAllByQuizId(quiz)).thenReturn(List.of(grading));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            quizService.quizGrades(1, request);
        });

        assertEquals("No logged in user is found.", exception.getMessage());
    }

    @Test
    void testQuizGrades_notInstructor()
    {
        when(sessionAuthService.requireUser(request)).thenReturn(studentUser);
        when(quizRepository.existsById(1)).thenReturn(true);
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        when(gradingRepository.findAllByQuizId(quiz)).thenReturn(List.of(grading));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            quizService.quizGrades(1, request);
        });

        assertEquals("Logged-in user is not an instructor.", exception.getMessage());
    }

    @Test
    void testQuizGrades_notQuizInstructor()
    {
        Users inValidInstructorUser = new Users();
        inValidInstructorUser.setUserId(3);
        inValidInstructorUser.setUserTypeId(instructorType);

        when(sessionAuthService.requireUser(request)).thenReturn(inValidInstructorUser);
        when(quizRepository.existsById(1)).thenReturn(true);
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        when(gradingRepository.findAllByQuizId(quiz)).thenReturn(List.of(grading));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            quizService.quizGrades(1, request);
        });

        assertEquals("Logged-in instructor does not have access for this quiz grades.", exception.getMessage());
    }

    @Test
    void testQuizGrades_quizNotFound()
    {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {
            quizService.quizGrades(2, request);
        });

        assertEquals("Quiz with ID 2 not found.", exception.getMessage());
    }

    @Test
    void testQuizGrades()
    {
        when(sessionAuthService.requireUser(request)).thenReturn(instructorUser);
        when(quizRepository.existsById(1)).thenReturn(true);
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));
        when(gradingRepository.findAllByQuizId(quiz)).thenReturn(List.of(grading));

        List <String> quizGrades = quizService.quizGrades(1, request);

        assertEquals(1, quizGrades.size());
    }

}
