package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.repository.UsersTypeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.verify;

class UsersTypeServiceTest {

    @Mock
    private UsersTypeRepository usersTypeRepository;

    @InjectMocks
    private UsersTypeService usersTypeService;

    public UsersTypeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_success() {
        usersTypeService.getAll();
        verify(usersTypeRepository).findAll();
    }
}
