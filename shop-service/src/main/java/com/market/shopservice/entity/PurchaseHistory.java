package com.market.shopservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "purchase_history")
public class PurchaseHistory {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Product product;
    private Long purchaseFrom;
    private LocalDateTime purchaseDate;

}
