package com.studio.smartPhotoService.repository;

import com.studio.smartPhotoService.entities.WeddingHost;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Repository used to have specific functions to deal with the database directly
 */
public interface WeddingHostRepo extends JpaRepository<WeddingHost, Long> {
}
