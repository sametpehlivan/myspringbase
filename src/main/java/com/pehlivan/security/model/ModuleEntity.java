package com.pehlivan.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Table(name = "modules")
@Entity
@Getter
@Setter
public class ModuleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "module_name",nullable = false)
    private String moduleName;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "moduleEntity",orphanRemoval = true)
    private Set<Permission> permissions;
}
