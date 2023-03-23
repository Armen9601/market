package com.market.shopservice.service;

import com.market.shopservice.dto.CommentDto;
import com.market.userservice.entity.User;

public interface CommentService {

    CommentDto addComment(User user, Long productId, String text);

    void delete(User user, Long productId);

}
