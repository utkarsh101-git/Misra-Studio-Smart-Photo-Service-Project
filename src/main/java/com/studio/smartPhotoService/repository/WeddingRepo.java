package com.studio.smartPhotoService.repository;

import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingHost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeddingRepo extends JpaRepository<Wedding, Long> {
    Optional<Wedding> findByWeddingIdAndIsDeletedFalse(Long weddingId);
    
    List<Wedding> findByWeddingHostAndIsDeletedFalse(WeddingHost weddingHost);

    Optional<Wedding> findByWeddingUniqueCodeAndIsDeletedFalse(String code);
}
