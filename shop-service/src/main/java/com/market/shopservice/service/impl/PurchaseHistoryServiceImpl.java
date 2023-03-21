package com.market.shopservice.service.impl;

import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.entity.PurchaseHistory;
import com.market.shopservice.mapper.PurchaseHistoryMapper;
import com.market.shopservice.repository.PurchaseHistoryRepository;
import com.market.shopservice.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private final PurchaseHistoryRepository repository;
    private final PurchaseHistoryMapper historyMapper;


    @Override
    public PurchaseHistoryDto save(PurchaseHistoryDto purchaseHistoryDto) {
        PurchaseHistory saved = repository.save(historyMapper.toEntity(purchaseHistoryDto));
        return historyMapper.toDto(saved);
    }
}
