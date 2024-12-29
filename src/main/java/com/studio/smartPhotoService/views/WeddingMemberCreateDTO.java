package com.studio.smartPhotoService.views;

import jakarta.validation.constraints.NotEmpty;
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
public class WeddingMemberCreateDTO {

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
