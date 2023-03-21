package com.market.shopservice.repository;

import com.market.shopservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByCreatorId(Long creatorId);

}
