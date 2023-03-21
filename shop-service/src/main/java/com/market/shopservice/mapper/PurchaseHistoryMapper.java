package com.market.shopservice.mapper;

import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.entity.PurchaseHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryMapper extends EntityMapper<PurchaseHistoryDto, PurchaseHistory> {


}
