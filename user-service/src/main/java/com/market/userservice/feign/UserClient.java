package com.market.userservice.feign;

import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-service", url = "http://localhost:8083/users")
public interface UserClient {

    @GetMapping("/email/{email}")
    UserDto findByEmail(@PathVariable String email);

    @GetMapping("/{id}")
    UserDto findById(@PathVariable("id") Long id);

    @PostMapping(value = "/")
    UserDto save(@RequestBody UserDto userDto);

    @PutMapping(value = "/")
    UserDto update(@RequestBody UserDto userDto);

}
