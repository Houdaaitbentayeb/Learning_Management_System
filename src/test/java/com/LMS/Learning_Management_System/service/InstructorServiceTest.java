package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Instructor;
import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.repository.InstructorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServiceTest {

    @Mock private InstructorRepository instructorRepository;
    @Mock private HttpServletRequest request;

    @InjectMocks
    private InstructorService instructorService;

    public InstructorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_success() {
        Users user = new Users();
        user.setUserId(1);

        Instructor instructor = new Instructor();
        instructor.setFirstName("A");
        instructor.setLastName("B");

        Instructor dbInstructor = new Instructor();

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(instructorRepository.getReferenceById(1)).thenReturn(dbInstructor);

        instructorService.save(1, instructor, request);

        verify(instructorRepository).save(dbInstructor);
    }

    @Test
    void save_notAuthorized() {
        Users user = new Users();
        user.setUserId(2);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        Instructor instructor = new Instructor();

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> instructorService.save(1, instructor, request)
        );

        assertEquals("You are not authorized to perform this action.", ex.getMessage());    }
}
