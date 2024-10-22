package com.studio.smartPhotoService.services;

import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingMember;
import com.studio.smartPhotoService.exceptions.WeddingMemberDoesNotExistsException;
import com.studio.smartPhotoService.exceptions.WeddingObjectAlreadyExistsException;
import com.studio.smartPhotoService.repository.WeddingMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class WeddingMemberService {

    @Autowired
    private WeddingMemberRepo weddingMemberRepo;

    @Autowired
    private WeddingService weddingService;

    public WeddingMember createWeddingMember(WeddingMember weddingMember){
        // Wedding Member has the code attached to WeddingMember
        // Fetch Wedding Object with code
        // Set Wedding Obj to Wedding member and save
        weddingMember.setAttendsWeddingSet(
                weddingMember.getAttendsWeddingSet().stream().map(wedding ->
                        weddingService.getWeddingForGivenWeddingCode(wedding.getWeddingUniqueCode()))
                        .collect(Collectors.toSet())
        );

        return weddingMemberRepo.save(weddingMember);
    }

    public WeddingMember getWeddingMemberById(Long weddingMemberId){
        return this.weddingMemberRepo.findById(weddingMemberId).orElseThrow(() -> new WeddingMemberDoesNotExistsException("Wedding member with Id %s does not exists".formatted(weddingMemberId)));
    }

    public WeddingMember registerToWeddingObjectByGivenId(Long weddingMemberId, String uniqueCode){
        WeddingMember weddingMember = this.getWeddingMemberById(weddingMemberId);
        Wedding weddingObj = this.weddingService.getWeddingForGivenWeddingCode(uniqueCode);

        // If WeddingObject is already added then it throw an Exception
        if(!weddingMember.getAttendsWeddingSet().add(weddingObj)){
            throw new WeddingObjectAlreadyExistsException("Wedding Object with code %s already registered to Wedding Member with id %s".formatted(uniqueCode, weddingMemberId));
        }
        return this.weddingMemberRepo.save(weddingMember);
    }

}
