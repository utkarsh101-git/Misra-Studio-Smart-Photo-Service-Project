package com.studio.smartPhotoService.services;

import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingHost;
import com.studio.smartPhotoService.exceptions.WeddingObjectDoesNotExistsException;
import com.studio.smartPhotoService.repository.WeddingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeddingService {

    @Autowired
    private WeddingRepo weddingRepo;


    public List<Wedding> getWeddingForGivenWeddingHost(WeddingHost weddingHost) {
        return this.weddingRepo.findByWeddingHost(weddingHost);
    }

    public Wedding getWeddingForGivenWeddingCode(String code) {
        Optional<Wedding> byWeddingUniqueCode = this.weddingRepo.findByWeddingUniqueCode(code);
        return byWeddingUniqueCode.orElseThrow(() -> new WeddingObjectDoesNotExistsException("Wedding Object does not exist with code %s".formatted(code)));
    }

    public Wedding getWeddingByWeddingId(Long weddingId) {
        return this.weddingRepo.findById(weddingId).orElseThrow(() -> new WeddingObjectDoesNotExistsException("Wedding Object with this %s wedding Id does not exists".formatted(weddingId)));
    }

}
