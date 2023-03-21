package com.market.shopservice.service;

import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.userservice.entity.User;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    ProductDto add(User user,ProductDto productDto);

    ProductDto findById(Long id);

    List<ProductDto> findAll(PageRequest pageRequest);

    void delete(Long id);

    PurchaseHistoryDto buy(User user, Long productId);

}
