package com.market.shopservice.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private double price;
    @NotNull
    private int count;
    private int rating;
    private SalaryDto salary;
    private double salaryAmount;
    private List<CommentDto> comment;
    private List<String> keywords;
    private CompanyDto company;

}
