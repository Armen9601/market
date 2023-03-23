package com.market.shopservice.service.impl;

import com.market.shopservice.dto.CompanyDto;
import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.entity.Activity;
import com.market.shopservice.entity.Product;
import com.market.shopservice.exception.HaveNotEnoughMoneyException;
import com.market.shopservice.exception.ProductHasNotInStorageException;
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
import java.util.ArrayList;
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

    public ProductDto update(ProductDto productDto) {
        findById(productDto.getId());
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    @Override
    public List<ProductDto> updateAll(List<ProductDto> productDto) {
        List<Product> products = productRepository.saveAll(productMapper.toEntity(productDto));
        return productMapper.toDto(products);
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<ProductDto> findAll(PageRequest pageRequest) {
        List<ProductDto> productDtos = productMapper.toDto(productRepository.findAll());
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductDto productDto : productDtos) {
            if (productDto.getCompany() != null && productDto.getCompany().getActivity() != Activity.BLOCKED) {
                productDtoList.add(productDto);
            } else if (productDto.getCompany() == null) {
                productDtoList.add(productDto);
            }
        }
        return productDtoList;
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
            UserDto byId = userClient.findById(user.getId());
            if (byId.getBalance() < productDto.getPrice()) {
                throw new HaveNotEnoughMoneyException();
            }
            PurchaseHistoryDto historyDto = PurchaseHistoryDto.builder()
                    .product(productDto)
                    .purchaseDate(LocalDateTime.now())
                    .purchaseFrom(byId.getId())
                    .build();
            historyService.save(historyDto);
            productDto.setCount(productDto.getCount() - 1);
            if (productDto.getCompany() != null) {
                transferProceedToCompany(productDto);
            }
            productRepository.save(productMapper.toEntity(productDto));
            byId.setBalance(byId.getBalance() - productDto.getPrice());
            userClient.update(byId);
            return historyDto;
        }
        throw new ProductHasNotInStorageException();
    }

    @Override
    public List<ProductDto> findByIds(List<Long> productIds) {
        return productMapper.toDto(productRepository.findAllByIdIsIn(productIds));
    }

    private double commissionCalculating(double productPrice) {
        double percent = productPrice * 0.05;
        return productPrice - percent;
    }

    private void transferProceedToCompany(ProductDto productDto) {
        double proceed = commissionCalculating(productDto.getPrice());
        Long id = productDto.getCompany().getCreatorId();
        UserDto companyCreator = userClient.findById(id);
        companyCreator.setBalance(companyCreator.getBalance() + proceed);
        userClient.save(companyCreator);
    }
}
