package com.studio.smartPhotoService.MapperUtils;

import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingMember;
import com.studio.smartPhotoService.views.WeddingMemberCreateDTO;
import com.studio.smartPhotoService.views.WeddingMemberResponseDTO;

import java.util.HashSet;
import java.util.stream.Collectors;

public class WeddingMemberMapper {
    public static WeddingMember mapToEntity(WeddingMemberCreateDTO weddingMemberCreateDTO) {
        WeddingMember newWeddingMember = new WeddingMember();
        newWeddingMember.setWeddingMemberId(weddingMemberCreateDTO.getWeddingMemberId());
        newWeddingMember.setWeddingMemberName(weddingMemberCreateDTO.getWeddingMemberName());
        newWeddingMember.setWeddingMemberEmailId(weddingMemberCreateDTO.getWeddingMemberEmailId());
        newWeddingMember.setWeddingMemberRelation(weddingMemberCreateDTO.getWeddingMemberRelation());

        // converting Strings to Wedding object
        if (!weddingMemberCreateDTO.getAttendsWeddingSet().isEmpty()) {
            newWeddingMember.setAttendsWeddingSet(weddingMemberCreateDTO.getAttendsWeddingSet().stream().map(code -> {
                Wedding wedding = new Wedding();
                wedding.setWeddingUniqueCode(code);
                return wedding;
            }).collect(Collectors.toSet()));
        }

        newWeddingMember.setWeddingMemberImagePathList(weddingMemberCreateDTO.getWeddingMemberImagePathList());


        return newWeddingMember;
    }

    public static WeddingMemberResponseDTO mapToDto(WeddingMember weddingMember) {

        return new WeddingMemberResponseDTO(
                weddingMember.getWeddingMemberId(),
                weddingMember.getWeddingMemberName(),
                weddingMember.getWeddingMemberEmailId(),
                weddingMember.getWeddingMemberRelation(),
                weddingMember.getAttendsWeddingSet().isEmpty() ? new HashSet<>() : weddingMember.getAttendsWeddingSet().stream().map(wedding -> WeddingMapper.mapToDTO(wedding)).collect(Collectors.toSet()),
                weddingMember.getWeddingMemberImagePathList()
        );

    }

}
