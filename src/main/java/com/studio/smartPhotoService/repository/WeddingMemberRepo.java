package com.studio.smartPhotoService.repository;

import com.studio.smartPhotoService.entities.WeddingMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeddingMemberRepo extends JpaRepository<WeddingMember, Long> {
    Optional<WeddingMember> findByWeddingMemberIdAndIsDeletedFalse(Long weddingMemberId);

}
