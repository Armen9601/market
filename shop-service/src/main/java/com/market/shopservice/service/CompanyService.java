package com.market.shopservice.service;

import com.market.shopservice.dto.CompanyDto;
import com.market.shopservice.entity.Activity;
import com.market.shopservice.entity.Status;
import com.market.userservice.entity.User;

import java.util.List;

public interface CompanyService {

    CompanyDto add(User user, CompanyDto companyDto);

    CompanyDto findById(Long id);

    void blockOrActivateCompany(Long id, User user, Activity activity);

    void approveCompany(Long id, User user, Status status);

    void delete(Long id);

    List<CompanyDto> findByUserId(Long userId);

    CompanyDto update(CompanyDto byId);
}
