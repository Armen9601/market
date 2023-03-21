package com.market.shopservice.dto;

import com.market.shopservice.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseHistoryDto {

    private Long id;
    private ProductDto product;
    private Long purchaseFrom;
    private LocalDateTime purchaseDate;

}
