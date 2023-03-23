package com.market.shopservice.service;

import com.market.shopservice.dto.ReturnProductDto;
import com.market.userservice.entity.User;

public interface ReturnProductService {


    ReturnProductDto returnProduct(User user, Long productId,int returnCount);

    void delete(Long id);
}
