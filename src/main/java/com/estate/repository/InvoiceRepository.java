package com.estate.repository;

import com.estate.repository.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    @Query("SELECT SUM(i.totalAmount) FROM InvoiceEntity i WHERE i.customer.id = :customerId")
    BigDecimal findTotalAmountByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT i " +
            "FROM InvoiceEntity i " +
            "JOIN i.customer cus " +
            "JOIN i.contract c " +
            "WHERE c.id = :contractId " +
            "AND cus.id = :customerId " +
            "AND i.month = :month " +
            "AND i.year = :year")
    InvoiceEntity getPreMonthInvoice(
            @Param("contractId") Long contractId,
            @Param("customerId") Long customerId,
            @Param("month") int month,
            @Param("year") int year
    );

}
