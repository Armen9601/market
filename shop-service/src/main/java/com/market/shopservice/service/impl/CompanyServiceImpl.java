package com.market.shopservice.service.impl;

import com.market.shopservice.dto.CompanyDto;
import com.market.shopservice.entity.Activity;
import com.market.shopservice.entity.Company;
import com.market.shopservice.entity.Status;
import com.market.shopservice.mapper.CompanyMapper;
import com.market.shopservice.repository.CompanyRepository;
import com.market.shopservice.service.CompanyService;
import com.market.userservice.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static com.market.userservice.entity.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDto add(User user, CompanyDto companyDto) {
        companyDto.setStatus(Status.PENDING);
        companyDto.setCreatorId(user.getId());
        Company company = companyMapper.toEntity(companyDto);
        Company saved = companyRepository.save(company);
        return companyMapper.toDto(saved);
    }

    @Override
    public CompanyDto findById(Long id) {
        return companyRepository.findById(id)
                .map(companyMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void blockOrActivateCompany(Long id, User user,Activity activity) {
        if (user.getRole().equals(ADMIN)) {
            CompanyDto companyDto = findById(id);
            companyDto.setActivity(activity);
            companyRepository.save(companyMapper.toEntity(companyDto));
        } else {
            throw new RuntimeException("you have not change company activity");
        }
    }

    @Override
    public void approveCompany(Long id, User user, Status status) {
        CompanyDto companyDto = findById(id);
        if (user.getRole().equals(ADMIN)&& companyDto.getStatus().equals(Status.PENDING)) {
            companyDto.setStatus(status);
            companyRepository.save(companyMapper.toEntity(companyDto));
        } else {
            throw new RuntimeException("you have not change company status");
        }
    }

    @Override
    public void delete(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("can not find Company by id:" + id));
        companyRepository.deleteById(id);
        log.info("delete company : {}", company.getName());
    }

    @Override
    public List<CompanyDto> findByUserId(Long userId) {
        return companyRepository.findAllByCreatorId(userId)
                .stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }
}
