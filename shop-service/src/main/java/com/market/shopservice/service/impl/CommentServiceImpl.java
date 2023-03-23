package com.market.shopservice.service.impl;

import com.market.shopservice.dto.CommentDto;
import com.market.shopservice.dto.PurchaseHistoryDto;
import com.market.shopservice.entity.Comment;
import com.market.shopservice.exception.CommentWritingBlockedException;
import com.market.shopservice.mapper.CommentMapper;
import com.market.shopservice.repository.CommentRepository;
import com.market.shopservice.service.CommentService;
import com.market.shopservice.service.PurchaseHistoryService;
import com.market.userservice.entity.User;
import com.market.userservice.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PurchaseHistoryService purchaseHistoryService;

    @Override
    public CommentDto addComment(User user, Long productId, String text) {
        PurchaseHistoryDto byPurchaseFromAndProductId = purchaseHistoryService.findByPurchaseFromAndProductId(user.getId(), productId);
        if (byPurchaseFromAndProductId != null) {
            CommentDto commentDto = CommentDto.builder()
                    .text(text)
                    .userId(user.getId())
                    .productId(productId)
                    .build();
            Comment saved = commentRepository.save(commentMapper.toEntity(commentDto));
            return commentMapper.toDto(saved);
        }
        throw new CommentWritingBlockedException();
    }

    @Override
    public void delete(User user, Long productId) {
        Comment comment = commentRepository.findByProductId(productId).orElseThrow(EntityNotFoundException::new);
        if (comment.getUserId().equals(user.getId())) {
            commentRepository.delete(comment);
        }
    }
}
