package com.smartcampus.springAuth2.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_user_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"fk_role_id","fk_user_id"}))
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long userRoleId;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity fkUserId;

    @ManyToOne
    @JoinColumn(name = "fk_role_id", referencedColumnName = "role_id", nullable = false)
    private RoleEntity fkRoleId;
}
