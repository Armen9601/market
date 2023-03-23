package com.market.shopservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoDto {

    private Long id;
    private Long companyId;
    private String filePath;

}
