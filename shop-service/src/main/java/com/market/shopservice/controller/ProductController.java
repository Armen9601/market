package com.market.shopservice.controller;

import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.service.ProductService;
import com.market.userservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ProductDto> add(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.add(currentUser.getUser(), productDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> findAll(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(productService.findAll(PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/buy/{productId}")
    public ResponseEntity<PurchaseHistoryDto> buyProduct(@AuthenticationPrincipal CurrentUser currentUser,
                                                         @PathVariable Long productId) {
        return ResponseEntity.ok(productService.buy(currentUser.getUser(), productId));
    }

}
