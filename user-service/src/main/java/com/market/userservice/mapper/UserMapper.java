package com.market.userservice.mapper;

import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {


}
