package com.pehlivan.security.model;

import com.pehlivan.security.security.AccountUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
    @OneToOne(
            mappedBy = "user" ,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private RefreshToken refreshToken;
    private Integer  failedLoginAttempt = 0;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @Column(name = "lock_date",nullable = true)
    private LocalDateTime lockDate;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "is_banned")
    private BanStatus isBanned;
    public void lock(){
        resetFailedInfo();
        var userFailedAttempt = this.getFailedLoginAttempt();
        this.setFailedLoginAttempt(userFailedAttempt+1);
        if( userFailedAttempt >= AccountUtils.MAX_ATTEMPT_SIZE ){
            this.setLockDate(lockDate());
        }
    }

    private void resetFailedInfo() {
        if (this.getLockDate() != null ){
            var to = LocalDateTime.now();
            var from = this.getLockDate();
            Duration duration = Duration.between(from, to);
            var hour = duration.toHours();
            if (hour > AccountUtils.RESET_LOCK_DATE_HOUR) {
                this.setFailedLoginAttempt(0);
                this.setLockDate(null);
            }
        }
    }
    private static LocalDateTime lockDate(){
        var stringDate  = LocalDateTime
                .now()
                .plusMinutes(AccountUtils.LOCK_TIME_MINUTE)
                .format(AccountUtils.LOCK_DATE_TIME_FORMAT);
        return LocalDateTime.parse(stringDate,AccountUtils.LOCK_DATE_TIME_FORMAT);

    }
    public boolean isBanned(){
        return isBanned ==  BanStatus.YES;
    }
    public boolean isLocked(){
        if (lockDate == null) return false;
        return LocalDateTime.now().isBefore(lockDate);
    }
}
