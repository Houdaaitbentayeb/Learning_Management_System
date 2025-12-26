package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userId")
public class Instructor {

    @Id
    private int userAccountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    private String firstName;
    private String lastName;

    public Instructor(Users savedUser) {
        this.userId = savedUser;
    }
}
