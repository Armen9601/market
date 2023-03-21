package com.market.shopservice.mapper;

import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {


}
