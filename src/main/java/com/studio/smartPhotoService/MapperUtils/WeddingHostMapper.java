package com.studio.smartPhotoService.MapperUtils;

import com.studio.smartPhotoService.entities.WeddingHost;
import com.studio.smartPhotoService.views.WeddingHostResponseDTO;

import java.util.HashSet;
import java.util.stream.Collectors;

public class WeddingHostMapper {
    public static WeddingHostResponseDTO mapToDTO(WeddingHost weddingHost) {
        return new WeddingHostResponseDTO(weddingHost.getWeddingHostId(),
                weddingHost.getWeddingHostName(),
                weddingHost.getWeddingHostEmailId(),
                weddingHost.getCreatedWeddingSet().isEmpty() ? new HashSet<>() : weddingHost.getCreatedWeddingSet().stream().map(wedding -> WeddingMapper.mapToDTO(wedding)).collect(Collectors.toSet())
        );
    }
}
