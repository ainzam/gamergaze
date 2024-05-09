package com.gamegaze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamegaze.domain.Publication;
import com.gamegaze.domain.User;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByCreatedBy(User user);
    @Query("SELECT p FROM Publication p WHERE p.createdBy IN (SELECT uf.followed FROM UserFollow uf WHERE uf.follower = :user)")
    List<Publication> findPublicationsByFollowedUsers(@Param("user") User user);
}