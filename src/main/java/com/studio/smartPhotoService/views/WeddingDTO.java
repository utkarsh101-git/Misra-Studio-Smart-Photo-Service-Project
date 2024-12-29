package com.studio.smartPhotoService.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingDTO {
    private Long weddingId;

    private String weddingUniqueCode;

    private String weddingTitle;

    private LocalDateTime weddingDateTime;

    private Map<String, LocalDateTime> weddingEventDates;

    private String weddingHostName;
}
