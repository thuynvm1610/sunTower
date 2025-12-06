package com.estate.repository;

import com.estate.repository.entity.InvoiceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    @Query("SELECT SUM(i.totalAmount) FROM InvoiceEntity i WHERE i.customer.id = :customerId")
    BigDecimal findTotalAmountByCustomerId(@Param("customerId") Long customerId);

    InvoiceEntity getFirstByCustomerIdAndStatus(Long customerId, String status);

    Long countByCustomerIdAndStatus(Long customerId, String status);

}
