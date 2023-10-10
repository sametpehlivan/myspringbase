package com.pehlivan.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "token")
    private UUID token;
    @OneToOne
    @JoinColumn(name = "user_id" ,referencedColumnName = "id")
    private User user;
    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
