package com.market.shopservice.dto;

import com.market.shopservice.entity.Activity;
import com.market.shopservice.entity.Logo;
import com.market.shopservice.entity.Product;
import com.market.shopservice.entity.Status;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {

    private Long id;
    private String name;
    private String description;
    private Long creatorId;
    private LogoDto logo;
    private List<ProductDto> products;
    private Status status;
    private Activity activity;

}
