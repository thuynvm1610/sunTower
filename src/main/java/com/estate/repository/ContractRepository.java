package com.estate.repository;

import com.estate.repository.custom.ContractRepositoryCustom;
import com.estate.repository.entity.ContractEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ContractRepository extends JpaRepository<ContractEntity, Long>, ContractRepositoryCustom {
    @Query("SELECT c.staff.id, c.staff.fullName, COUNT(c.id) " +
            "FROM ContractEntity c " +
            "GROUP BY c.staff.id, c.staff.fullName " +
            "ORDER BY COUNT(c.id) DESC")
    List<Object[]> countContractsByStaff(Pageable pageable);

    List<ContractEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDateTime endOfYear, LocalDateTime startOfYear
    );

    @Query("SELECT b.name, COUNT(c) " +
            "FROM ContractEntity c JOIN c.building b " +
            "GROUP BY b.name " +
            "ORDER BY COUNT(c) DESC")
    List<Object[]> countContractsByBuilding(Pageable pageable);

    @Query("SELECT YEAR(c.startDate), COUNT(c) " +
            "FROM ContractEntity c " +
            "GROUP BY YEAR(c.startDate) " +
            "ORDER BY YEAR(c.startDate)")
    List<Long[]> countContractsByYear();

    long countByBuildingId(Long buildingId);

    long countByCustomerId(Long customerId);

    @Query("""
        SELECT c
        FROM ContractEntity c
        JOIN c.customer cus
        LEFT JOIN InvoiceEntity i 
            ON i.contract = c 
           AND i.month = :month 
           AND i.year = :year
        WHERE cus.id = :customerId
          AND (i IS NULL OR i.status != 'PAID')
    """)
    List<ContractEntity> getHaveNotPaidContracts(
            @Param("customerId") Long customerId,
            @Param("month") Integer month,
            @Param("year") Integer year
    );


}
