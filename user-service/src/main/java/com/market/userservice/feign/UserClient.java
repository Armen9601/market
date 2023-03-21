package com.market.userservice.feign;

import com.market.userservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service", url = "http://localhost:8083/users")
public interface UserClient {

    @GetMapping("/email/{email}")
    UserDto findByEmail(@PathVariable String email);

    @GetMapping("/id/{id}")
    UserDto findById(@PathVariable Long id);

    @PostMapping(value = "/")
    UserDto save(@RequestBody UserDto userDto);

}
