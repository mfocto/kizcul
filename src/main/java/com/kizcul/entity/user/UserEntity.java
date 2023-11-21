package com.kizcul.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="User")
public class UserEntity {
    @Id
    private String userIdx;
    @Column
    private String userId;
    @Column
    private String password;
    @Column
    private String userAuth;
    @Column
    private String role;
}
