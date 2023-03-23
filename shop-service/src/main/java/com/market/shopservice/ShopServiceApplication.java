package com.market.shopservice;

import com.market.userservice.util.JwtTokenParser;
import com.market.userservice.util.SecurityRegistrar;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients({
        "com.market.userservice.feign"
})
public class ShopServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private ModelMapper modelMapper;

    @Bean
    public JwtTokenParser tokenParser() {
        return new JwtTokenParser();
    }

    @Bean
    public SecurityRegistrar securityRegistrar() {
        return new SecurityRegistrar(tokenParser(), modelMapper);
    }

}
