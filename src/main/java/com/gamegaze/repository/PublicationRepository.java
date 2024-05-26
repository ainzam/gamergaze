package com.gamegaze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findByUserOrderByCreatedAtDesc(User user);
    List<Publication> findByGameIdOrderByCreatedAtDesc(Long gameId);
    
}