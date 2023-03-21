package com.market.shopservice.mapper;

import com.market.shopservice.dto.SalaryDto;
import com.market.shopservice.entity.Salary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalaryMapper extends EntityMapper<SalaryDto, Salary> {


}
