package com.market.shopservice.mapper;

import com.market.shopservice.dto.CommentDto;
import com.market.shopservice.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDto, Comment> {


}
