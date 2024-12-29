package com.studio.smartPhotoService.MapperUtils;

import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.views.WeddingDTO;

public class WeddingMapper {
    public static WeddingDTO mapToDTO(Wedding wedding) {
        return new WeddingDTO(wedding.getWeddingId(),
                wedding.getWeddingUniqueCode(),
                wedding.getWeddingTitle(),
                wedding.getWeddingDateTime(),
                wedding.getWeddingEventDates(),
                wedding.getWeddingHost().getWeddingHostName());
    }
}
