package com.market.shopservice.service.impl;

import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.dto.SalaryDto;
import com.market.shopservice.entity.Salary;
import com.market.shopservice.mapper.SalaryMapper;
import com.market.shopservice.repository.SalaryRepository;
import com.market.shopservice.service.ProductService;
import com.market.shopservice.service.SalaryService;
import com.market.shopservice.util.DayScheduler;
import com.market.userservice.entity.User;
import com.market.userservice.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final SalaryMapper salaryMapper;
    private final ProductService productService;
    private final DayScheduler dayScheduler;

    @Override
    public SalaryDto add(User user, SalaryDto salaryDto) {
        List<ProductDto> products = productService.findByIds(salaryDto.getSalaryProductsId());
        List<ProductDto> dtoList = products.stream()
                .peek(product -> {
                    double salary = product.getPrice() * salaryDto.getSalaryCount() / 100;
                    product.setPrice(product.getPrice() - salary);
                    product.setSalaryAmount(salary);
                }).collect(Collectors.toList());
        productService.updateAll(dtoList);
        dayScheduler.schedule(salaryDto);
        Salary save = salaryRepository.save(salaryMapper.toEntity(salaryDto));
        return salaryMapper.toDto(save);
    }

    @Override
    public void delete(User user, Long id) {
        if (user.getRole().equals(UserRole.ADMIN))
            salaryRepository.deleteById(id);
    }

}
