package com.market.shopservice.mapper;

import com.market.shopservice.dto.LogoDto;
import com.market.shopservice.entity.Logo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogoMapper extends EntityMapper<LogoDto, Logo> {


}
