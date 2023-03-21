package com.market.shopservice.dto;

import com.market.shopservice.entity.Comment;
import com.market.shopservice.entity.Company;
import com.market.shopservice.entity.Salary;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
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
    private List<CommentDto> comment;
    private List<String> keywords;
    private CompanyDto company;

}
