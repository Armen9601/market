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
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Long creatorId;
    @OneToOne
    private Logo logo;
    @OneToMany
    private List<Product> products;
    private Status status;
    private Activity activity;

}
