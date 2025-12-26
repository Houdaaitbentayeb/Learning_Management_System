package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class SessionAuthService {

    private final UsersRepository usersRepository;

    public SessionAuthService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Returns the currently logged-in user.
     * Throws an exception if no user is logged in or user does not exist.
     */
    public Users requireUser(HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("USER_ID");

        if (userId == null) {
            throw new IllegalArgumentException("No user is logged in.");
        }

        return usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    /**
     * Returns the logged-in user ID or null if not logged in.
     * Useful for optional authentication checks.
     */
    public Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute("USER_ID");
    }

    /**
     * Clears the current session (logout helper).
     */
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
