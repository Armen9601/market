package com.market.shopservice.service.impl;

import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.entity.PurchaseHistory;
import com.market.shopservice.mapper.PurchaseHistoryMapper;
import com.market.shopservice.repository.PurchaseHistoryRepository;
import com.market.shopservice.service.PurchaseHistoryService;
import com.market.userservice.entity.User;
import com.market.userservice.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<PurchaseHistoryDto> findByPurchaseFrom(Long purchaseFrom) {
        return historyMapper.toDto(repository.findByPurchaseFrom(purchaseFrom));
    }

    @Override
    public List<PurchaseHistoryDto> findByPurchaseFromAndProductId(User user, Long purchaseFrom, Long productId) {
        if (user.getRole().equals(UserRole.USER)) {
            purchaseFrom = user.getId();
        }
        return historyMapper.toDto(repository.findByPurchaseFromAndProductId(purchaseFrom, productId));
    }

    @Override
    public List<PurchaseHistoryDto> findAll(User user) {
        if (user.getRole().equals(UserRole.ADMIN)) {
            return historyMapper.toDto(repository.findAll());
        }
        return null;
    }

    @Override
    public List<PurchaseHistoryDto> findByPurchaseFromAndPurchaseDate(User user, Long productId) {
        return historyMapper.toDto(repository.findByPurchaseFromAndPurchaseDate(user.getId(), productId));
    }

    @Override
    public PurchaseHistoryDto findByPurchaseFromAndProductId(Long purchaseFrom, Long productId) {
        return historyMapper.toDto(repository.findOneByPurchaseFromAndProductId(purchaseFrom, productId));
    }
}
