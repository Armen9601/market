package com.market.shopservice.service.impl;

import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.dto.ReturnProductDto;
import com.market.shopservice.entity.ReturnStatus;
import com.market.shopservice.mapper.ReturnProductMapper;
import com.market.shopservice.repository.ReturnProductRepository;
import com.market.shopservice.service.ProductService;
import com.market.shopservice.service.PurchaseHistoryService;
import com.market.shopservice.service.ReturnProductService;
import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.User;
import com.market.userservice.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnProductServiceImpl implements ReturnProductService {

    private final ReturnProductRepository repository;
    private final ReturnProductMapper mapper;
    private final PurchaseHistoryService purchaseHistoryService;
    private final ProductService productService;
    private final UserClient userClient;

    @Override
    @Transactional
    public ReturnProductDto returnProduct(User user, Long productId, int returnCount) {
        List<PurchaseHistoryDto> historyDtos = purchaseHistoryService.findByPurchaseFromAndPurchaseDate(user, productId);
        List<ReturnProductDto> returnProducts = new ArrayList<>();
        if (historyDtos.size() >= returnCount) {
            for (int i = 0; i < returnCount; i++) {
                ReturnProductDto returnProductDto = ReturnProductDto.builder()
                        .product(historyDtos.get(i).getProduct())
                        .returnTime(LocalDateTime.now())
                        .purchasedFrom(user.getId())
                        .status(ReturnStatus.APPROVED)
                        .build();
                returnProducts.add(returnProductDto);
            }
            repository.saveAll(mapper.toEntity(returnProducts));
            ProductDto productDto = productService.findById(productId);
            productDto.setCount(productDto.getCount() + returnCount);
            productService.update(productDto);
            double returnMoney = productDto.getPrice() * returnCount;
            returnMoneyToBuyer(user.getId(), returnMoney);
            withdrawMoneyFromSeller(productDto.getCompany().getCreatorId(), returnMoney);
        }
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private void returnMoneyToBuyer(Long userId, double returnMoney) {
        UserDto userDto = userClient.findById(userId);
        userDto.setBalance(userDto.getBalance() + returnMoney);
        userClient.update(userDto);
    }

    private void withdrawMoneyFromSeller(Long sellerId, double withdrawMoney) {
        UserDto byId = userClient.findById(sellerId);
        byId.setBalance(byId.getBalance() - withdrawMoney);
        userClient.update(byId);
    }
}
