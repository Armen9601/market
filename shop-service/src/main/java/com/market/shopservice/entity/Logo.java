package com.market.shopservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "logo")
public class Logo {

    @Id
    @GeneratedValue
    private Long id;
    private String filePath;

}
