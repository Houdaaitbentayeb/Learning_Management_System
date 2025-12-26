package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userId")
public class Student {

    @Id
    private int userAccountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    private String firstName;
    private String lastName;

    public Student(Users savedUser) {
        this.userId = savedUser;
    }
}
