package com.market.shopservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue
    private Long id;
    private double salaryCount;
    private int salaryDays;
    @ElementCollection
    private List<Long> salaryProductsId;

}
