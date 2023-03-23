package com.market.shopservice.service;

import com.market.shopservice.dto.SalaryDto;
import com.market.userservice.entity.User;

public interface SalaryService {

    SalaryDto add(User user, SalaryDto salaryDto);

    void delete(User user, Long id);

}
