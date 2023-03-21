package com.market.shopservice.dto;

import com.market.shopservice.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryDto {

    private Long id;
    private double salaryCount;
    private int salaryDays;
    private List<ProductDto> salaryProducts;

}
