package com.studio.smartPhotoService.repository;

import com.studio.smartPhotoService.entities.WeddingMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeddingMemberRepo extends JpaRepository<WeddingMember, Long> {
}
