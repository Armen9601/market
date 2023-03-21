package com.market.shopservice.dto;

import com.market.shopservice.entity.Product;
import com.market.shopservice.entity.ReturnStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnProductDto {

    private Long id;
    private ProductDto product;
    private Long purchasedFrom;
    private LocalDateTime returnTime;
    private ReturnStatus status;

}
