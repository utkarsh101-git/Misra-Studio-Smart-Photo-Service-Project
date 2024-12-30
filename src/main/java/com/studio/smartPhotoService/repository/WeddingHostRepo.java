package com.studio.smartPhotoService.repository;

import com.studio.smartPhotoService.entities.WeddingHost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
Repository used to have specific functions to deal with the database directly
 */
public interface WeddingHostRepo extends JpaRepository<WeddingHost, Long> {
    Optional<WeddingHost> findByWeddingHostIdAndIsDeletedFalse(Long id);

    List<WeddingHost> findAllByIsDeletedFalse();

    Optional<WeddingHost> findByWeddingHostEmailIdAndIsDeletedFalse(String weddingHostEmailId);
}
