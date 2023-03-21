package com.market.shopservice.service.impl;

import com.market.shopservice.dto.CompanyDto;
import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.entity.Product;
import com.market.shopservice.mapper.ProductMapper;
import com.market.shopservice.repository.ProductRepository;
import com.market.shopservice.service.CompanyService;
import com.market.shopservice.service.ProductService;
import com.market.shopservice.service.PurchaseHistoryService;
import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.User;
import com.market.userservice.entity.UserRole;
import com.market.userservice.feign.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CompanyService companyService;
    private final PurchaseHistoryService historyService;
    private final UserClient userClient;

    @Override
    public ProductDto add(User user, ProductDto productDto) {
        if (user.getRole().equals(UserRole.ADMIN)) {
            Product saved = productRepository.save(productMapper.toEntity(productDto));
            return productMapper.toDto(saved);
        } else if (user.getRole().equals(UserRole.USER)) {
            List<CompanyDto> byUserId = companyService.findByUserId(user.getId());
            if (byUserId == null) {
                throw new RuntimeException("You can not add product");
            } else if (productDto.getCompany() == null) {
                throw new RuntimeException("Company name can not be null");
            }
            Product saved = productRepository.save(productMapper.toEntity(productDto));
            return productMapper.toDto(saved);
        }
        throw new RuntimeException("You can not add product");
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<ProductDto> findAll(PageRequest pageRequest) {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("can not find Product by id:" + id));
        productRepository.deleteById(id);
        log.info("delete product : {}", product.getName());
    }

    @Override
    @Transactional
    public PurchaseHistoryDto buy(User user, Long productId) {
        ProductDto productDto = findById(productId);
        if (productDto.getCount() > 0) {
            if (user.getBalance() < productDto.getPrice()) {
                throw new RuntimeException("You have not enough money");
            }
            PurchaseHistoryDto historyDto = PurchaseHistoryDto.builder()
                    .product(productDto)
                    .purchaseDate(LocalDateTime.now())
                    .purchaseFrom(user.getId())
                    .build();
            historyService.save(historyDto);
            productDto.setCount(productDto.getCount() - 1);
            if (productDto.getCompany() != null) {
                transferProceedToCompany(productDto);
            }
            productRepository.save(productMapper.toEntity(productDto));
            return historyDto;
        }
        throw new RuntimeException("We have not this product in storage");
    }

    private double commissionCalculating(double productPrice) {
        double percent = productPrice * 0.05;
        return productPrice - percent;
    }

    private void transferProceedToCompany(ProductDto productDto) {
        double proceed = commissionCalculating(productDto.getPrice());
        Long creatorId = productDto.getCompany().getCreatorId();
//        UserDto companyCreator = userClient.findById(creatorId);
//        companyCreator.setBalance(companyCreator.getBalance() + proceed);
//        userClient.save(companyCreator);
    }
}
