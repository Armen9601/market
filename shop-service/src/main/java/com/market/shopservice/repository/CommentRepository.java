package com.market.shopservice.repository;

import com.market.shopservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByProductId(Long productId);


}
