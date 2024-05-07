package com.gamegaze.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegaze.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
}