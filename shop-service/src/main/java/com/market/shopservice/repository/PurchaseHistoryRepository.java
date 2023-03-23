package com.market.shopservice.repository;

import com.market.shopservice.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

    List<PurchaseHistory> findByPurchaseFrom(Long purchaseFrom);

    @Query(nativeQuery = true, value = "select * from purchase_history where purchase_from = :purchaseFrom and product_id=:productId")
    List<PurchaseHistory> findByPurchaseFromAndProductId(@Param("purchaseFrom") Long purchaseFrom, @Param("productId") Long productId);

    @Query(nativeQuery = true, value = "select distinct from purchase_history where purchase_from = :purchaseFrom and product_id=:productId")
    PurchaseHistory findOneByPurchaseFromAndProductId(@Param("purchaseFrom") Long purchaseFrom, @Param("productId") Long productId);

    @Query(nativeQuery = true, value = "select * from purchase_history where purchase_date>= NOW() - INTERVAL '1 DAY' and purchase_from = :purchaseFrom and product_id=:productId")
    List<PurchaseHistory> findByPurchaseFromAndPurchaseDate(@Param("purchaseFrom") Long purchaseFrom, @Param("productId") Long productId);


}
