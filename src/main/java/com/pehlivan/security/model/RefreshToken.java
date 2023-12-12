package com.pehlivan.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Setter
@Getter
public class RefreshToken{
    @Id
    @UuidGenerator(style =  UuidGenerator.Style.RANDOM)
    @Column(name = "token")
    private String token;
    @OneToOne
    @JoinColumn(name = "user_id" ,referencedColumnName = "id")
    private User user;
    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
