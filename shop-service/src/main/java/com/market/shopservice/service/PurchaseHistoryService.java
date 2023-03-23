package com.market.shopservice.service;

import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.userservice.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseHistoryService {

    PurchaseHistoryDto save(PurchaseHistoryDto purchaseHistoryDto);

    List<PurchaseHistoryDto> findByPurchaseFrom(Long purchaseFrom);

    List<PurchaseHistoryDto> findByPurchaseFromAndProductId(User user, Long purchaseFrom, Long productId);

    PurchaseHistoryDto findByPurchaseFromAndProductId(Long purchaseFrom, Long productId);

    List<PurchaseHistoryDto> findAll(User user);

    List<PurchaseHistoryDto> findByPurchaseFromAndPurchaseDate(User user, Long productId);



}
