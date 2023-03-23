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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private double price;
    private int count;
    private int rating;
    @OneToOne
    private Salary salary;
    private double salaryAmount;
    @OneToMany
    private List<Comment> comment;
    @ElementCollection
    private List<String> keywords;
    @ManyToOne
    private Company company;

}
