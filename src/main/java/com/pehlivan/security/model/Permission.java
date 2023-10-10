package com.pehlivan.security.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Table(name = "permissions")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "permission_name")
    private String permissionName;
    @Column(name = "code", nullable = false)
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private ModuleEntity moduleEntity;

}
