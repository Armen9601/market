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
@Table(name = "return_product")
public class ReturnProduct {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Product product;
    private Long purchasedFrom;
    private LocalDateTime returnTime;
    private ReturnStatus status;
    private int count;

}
