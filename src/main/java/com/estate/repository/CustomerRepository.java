package com.estate.repository;

import com.estate.repository.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query("SELECT c.id, c.fullName, COUNT(co) " +
            "FROM CustomerEntity c JOIN c.contracts co " +
            "GROUP BY c.id, c.fullName " +
            "ORDER BY COUNT(co) DESC")
    List<Object[]> countContractsByCustomer(Pageable pageable);

    Page<CustomerEntity> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
