package com.studio.smartPhotoService.MapperUtils;

import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingMember;
import com.studio.smartPhotoService.views.WeddingMemberDTO;

import java.util.HashSet;
import java.util.stream.Collectors;

public class WeddingMemberMapper {
    public static WeddingMember mapToEntity(WeddingMemberDTO weddingMemberDTO) {
        WeddingMember newWeddingMember = new WeddingMember();
        newWeddingMember.setWeddingMemberId(weddingMemberDTO.getWeddingMemberId());
        newWeddingMember.setWeddingMemberName(weddingMemberDTO.getWeddingMemberName());
        newWeddingMember.setWeddingMemberEmailId(weddingMemberDTO.getWeddingMemberEmailId());
        newWeddingMember.setWeddingMemberRelation(weddingMemberDTO.getWeddingMemberRelation());

        // converting Strings to Wedding object
        if (!weddingMemberDTO.getAttendsWeddingSet().isEmpty()) {
            newWeddingMember.setAttendsWeddingSet(weddingMemberDTO.getAttendsWeddingSet().stream().map(code -> {
                Wedding wedding = new Wedding();
                wedding.setWeddingUniqueCode(code);
                return wedding;
            }).collect(Collectors.toSet()));
        }

        newWeddingMember.setWeddingMemberImagePathList(weddingMemberDTO.getWeddingMemberImagePathList());


        return newWeddingMember;
    }

    public static WeddingMemberDTO mapToDto(WeddingMember weddingMember) {

        return new WeddingMemberDTO(
                weddingMember.getWeddingMemberId(),
                weddingMember.getWeddingMemberName(),
                weddingMember.getWeddingMemberEmailId(),
                weddingMember.getWeddingMemberRelation(),
                weddingMember.getAttendsWeddingSet().isEmpty() ? new HashSet<>() :
                        weddingMember.getAttendsWeddingSet().stream().map(Wedding::getWeddingUniqueCode).collect(Collectors.toSet()),
                weddingMember.getWeddingMemberImagePathList()
        );

    }

}
