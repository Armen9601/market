package com.market.shopservice.controller;

import com.market.shopservice.dto.ReturnProductDto;
import com.market.shopservice.service.ReturnProductService;
import com.market.userservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/return-product")
@RequiredArgsConstructor
public class ReturnProductController {

    private final ReturnProductService returnProductService;

    @PostMapping("/return/{productId}/{returnCount}")
    public ResponseEntity<ReturnProductDto> returnProduct(@AuthenticationPrincipal CurrentUser currentUser,
                                                          @PathVariable Long productId,
                                                          @PathVariable int returnCount) {
        return ResponseEntity.ok(returnProductService.returnProduct(currentUser.getUser(), productId, returnCount));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        returnProductService.delete(id);
    }

}
