package com.market.shopservice.mapper;

import com.market.shopservice.dto.CommentDto;
import com.market.shopservice.dto.CompanyDto;
import com.market.shopservice.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDto, Company> {


}
