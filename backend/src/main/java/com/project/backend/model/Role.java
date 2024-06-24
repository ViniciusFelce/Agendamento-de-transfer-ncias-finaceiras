package com.project.backend.model;

import com.project.backend.security.EPermits;
import jakarta.persistence.*;


@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EPermits name;

    public Role() {
    }

    public Role(EPermits name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EPermits getName() {
        return name;
    }

    public void setName(EPermits name) {
        this.name = name;
    }
}
