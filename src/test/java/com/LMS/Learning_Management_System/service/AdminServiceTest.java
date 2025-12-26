package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Admin;
import com.LMS.Learning_Management_System.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    public AdminServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAdmin_success() {
        Admin admin = new Admin();
        adminService.save(admin);
        verify(adminRepository).save(admin);
    }
}
