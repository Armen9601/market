package com.market.authservice.mapper;

import com.market.authservice.model.User;
import com.market.userservice.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {


}
