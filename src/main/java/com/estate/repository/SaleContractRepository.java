package com.estate.repository;

import com.estate.repository.custom.SaleContractRepositoryCustom;
import com.estate.repository.entity.SaleContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleContractRepository extends JpaRepository<SaleContractEntity, Long>, SaleContractRepositoryCustom {
    @Query("""
                SELECT COUNT(sc)
                FROM SaleContractEntity sc
                JOIN sc.building b
                WHERE b.id = :buildingId
            """)
    Long saleContractCnt(@Param("buildingId") Long buildingId);
}
