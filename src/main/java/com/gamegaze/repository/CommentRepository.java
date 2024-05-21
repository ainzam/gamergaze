package com.gamegaze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Comment;
import com.gamegaze.domain.Publication;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPublicationOrderByCreatedAt(Publication publication);
}
