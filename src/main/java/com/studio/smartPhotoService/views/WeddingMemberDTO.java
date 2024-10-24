package com.studio.smartPhotoService.views;

import com.studio.smartPhotoService.entities.Wedding;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeddingMemberDTO {

    private Long weddingMemberId;

    @NotEmpty
    private String weddingMemberName;

    @NotEmpty
    private String weddingMemberEmailId;

    // Member Relation can be empty of null also
    private String weddingMemberRelation;

    private Set<String> attendsWeddingSet = new HashSet<>();

    // can be null initially , it may have some values after photos are clicked
    private List<String> weddingMemberImagePathList;

}
