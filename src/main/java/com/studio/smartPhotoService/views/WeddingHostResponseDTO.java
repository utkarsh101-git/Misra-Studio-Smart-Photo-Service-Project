package com.studio.smartPhotoService.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingHostResponseDTO {
    private Long weddingHostId;

    private String weddingHostName;

    private String weddingHostEmailId;

    private Set<WeddingDTO> createdWeddingSet = new HashSet<>();
}
