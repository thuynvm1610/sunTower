package com.estate.repository.entity;

import com.estate.repository.entity.BuildingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "rent_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer value;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private BuildingEntity building;

    public RentAreaEntity(Integer value, BuildingEntity building) {
        this.value = value;
        this.building = building;
    }

}
