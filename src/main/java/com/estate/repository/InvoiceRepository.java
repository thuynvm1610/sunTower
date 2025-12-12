package com.estate.repository;

import com.estate.repository.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    @Query("SELECT SUM(i.totalAmount) FROM InvoiceEntity i WHERE i.customer.id = :customerId")
    BigDecimal findTotalAmountByCustomerId(@Param("customerId") Long customerId);

    InvoiceEntity getFirstByCustomerIdAndStatus(Long customerId, String status);

    Long countByCustomerIdAndStatus(Long customerId, String status);

    List<InvoiceEntity> findAllByCustomerIdAndStatus(Long customerId, String status);

    @Query("""
        SELECT i FROM InvoiceEntity i
        JOIN i.customer c
        WHERE c.id = :customerId
          AND i.status = :status
          AND (:month IS NULL OR i.month = :month)
          AND (:year IS NULL OR i.year = :year)
        """)
    Page<InvoiceEntity> search(
            @Param("month") Integer month,
            @Param("year") Integer year,
            @Param("customerId") Long customerId,
            @Param("status") String status,
            Pageable pageable
    );
}
