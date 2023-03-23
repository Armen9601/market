package com.market.shopservice.controller;

import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.service.PurchaseHistoryService;
import com.market.userservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-history")
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @PostMapping
    public ResponseEntity<PurchaseHistoryDto> save(@RequestBody PurchaseHistoryDto purchaseHistoryDto) {
        PurchaseHistoryDto savedPurchaseHistoryDto = purchaseHistoryService.save(purchaseHistoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPurchaseHistoryDto);
    }

    @GetMapping("/{purchaseFrom}")
    public ResponseEntity<List<PurchaseHistoryDto>> findByPurchaseFrom(
            @PathVariable Long purchaseFrom) {
        List<PurchaseHistoryDto> purchaseHistoryDtos = purchaseHistoryService.findByPurchaseFrom(purchaseFrom);
        return ResponseEntity.ok(purchaseHistoryDtos);
    }

    @GetMapping("/{purchaseFrom}/{productId}")
    public ResponseEntity<List<PurchaseHistoryDto>> findByPurchaseFromAndProductId(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable Long purchaseFrom,
            @PathVariable Long productId) {
        List<PurchaseHistoryDto> purchaseHistoryDtos =
                purchaseHistoryService.findByPurchaseFromAndProductId(currentUser.getUser(), purchaseFrom, productId);
        return ResponseEntity.ok(purchaseHistoryDtos);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PurchaseHistoryDto>> findAll(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(purchaseHistoryService.findAll(currentUser.getUser()));
    }

    @GetMapping("/history/{productId}")
    public ResponseEntity<List<PurchaseHistoryDto>> findByPurchaseFromAndPurchaseDate(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                      @PathVariable Long productId) {
        return ResponseEntity.ok(purchaseHistoryService.findByPurchaseFromAndPurchaseDate(currentUser.getUser(), productId));
    }
}