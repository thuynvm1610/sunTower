package com.estate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class PlanningMapDTO {
    private Long id;
    private String mapType;
    private String issuedBy;
    private LocalDate issuedDate;
    private LocalDate expiredDate;
    private String imageUrl;
    private String documentUrl;
    private String note;
    private LocalDateTime createdDate;
    // true nếu expiredDate < today
    private boolean expired;
}