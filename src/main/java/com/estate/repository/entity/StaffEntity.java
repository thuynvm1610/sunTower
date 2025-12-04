package com.estate.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String role; // ADMIN, STAFF

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        createdDate = now;
        modifiedDate = now;
    }

    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = new Date();
    }

    // =================== RELATIONSHIPS ===================

    @ManyToMany(mappedBy = "staffs_buildings")
    private List<BuildingEntity> buildings = new ArrayList<>();

    @ManyToMany(mappedBy = "staffs_customers")
    private List<CustomerEntity> customers = new ArrayList<>();;
}
