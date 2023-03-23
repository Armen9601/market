package com.market.shopservice.mapper;

import com.market.shopservice.dto.ReturnProductDto;
import com.market.shopservice.entity.ReturnProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReturnProductMapper extends EntityMapper<ReturnProductDto, ReturnProduct> {


}
