package com.smartcampus.springAuth2.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "pege_id", length = 15, nullable = false)
    private String pegeId;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "second_name", length = 50)
    private String secondName;

    @Column(length = 50, nullable = false)
    private String surname;

    @Column(name = "second_surname",length = 50)
    private String secondSurname;

    @Column(name = "document_number", length = 20, nullable = false)
    private String documentNumber;

    @Column(name = "personal_email", length = 80, nullable = false, unique = true)
    private String personalEmail;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "fkUserId")
    private List<UserRoleEntity> userRoles;


}
