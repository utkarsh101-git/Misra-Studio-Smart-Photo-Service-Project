package com.studio.smartPhotoService.services;


import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingHost;
import com.studio.smartPhotoService.exceptions.WeddingHostAlreadyExists;
import com.studio.smartPhotoService.exceptions.WeddingHostDoesNotExistException;
import com.studio.smartPhotoService.exceptions.WeddingObjectAlreadyExistsException;
import com.studio.smartPhotoService.exceptions.WeddingObjectDoesNotExistsException;
import com.studio.smartPhotoService.repository.WeddingHostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
This service handles all request delegated from WeddingHostController
Service majorly handles CRUD operation for wedding host

 */
@Service
public class WeddingHostService {

    Logger logger = Logger.getLogger(WeddingHostService.class.getName());
    @Autowired
    private WeddingHostRepo weddingHostRepo;

    @Autowired
    private WeddingService weddingService;

    /*
    This method finds all email id and wraps them inside a list and returns it
     */
    public List<String> getAllWeddingHostEmails() {
        return this.weddingHostRepo.findAll().stream()
                .map(WeddingHost::getWeddingHostEmailId).toList();
    }


    /*
    This method creates a wedding host entity in the database with following conditions
        It checks whether the wedding host's emailId is already in use, if yes, then throws Exception
        Also creates a unique id (sharable) for every host entity  and finally persist the entity
     */
    public WeddingHost createWeddingHost(WeddingHost weddingHost) {

        Optional<String> optional = this.getAllWeddingHostEmails().stream().filter(emails -> emails.equals(weddingHost.getWeddingHostEmailId())).findAny();
        String foundEmail = optional.orElse(null);
        if (foundEmail != null) {
            throw new WeddingHostAlreadyExists("Wedding Host already exists - email Id of Wedding Host already exists");
        }

        // setting unique code to wedding Object
        // ***REMOVED*** linking every Wedding Object present inside WeddingHost with its WeddingHost
        weddingHost.getCreatedWeddingSet().forEach(weddingObj -> {
            weddingObj.setWeddingUniqueCode(UUID.randomUUID().toString());
            //weddingHost.addWeddingObj(weddingObj);
        });

        return this.weddingHostRepo.save(weddingHost);
    }

    public WeddingHost getWeddingHostById(Long weddingHostId) {
        Optional<WeddingHost> byId = this.weddingHostRepo.findById(weddingHostId);
        WeddingHost weddingHost = byId.orElseThrow(() -> new WeddingHostDoesNotExistException("Wedding Host does not exist for this given %s Id".formatted(weddingHostId)));

        return weddingHost;
    }

    public List<WeddingHost> getAllWeddingHost() {
        return this.weddingHostRepo.findAll();
    }

    public WeddingHost addNewWeddingObjectToWeddingHost(Wedding newWeddingObject, Long weddingHostId) {

        // If Wedding Object id == Null
        WeddingHost weddingHost = this.getWeddingHostById(weddingHostId);
        if (newWeddingObject.getWeddingId() == null) {
            // If code == null or blank or empty
            if (newWeddingObject.getWeddingUniqueCode() == null || newWeddingObject.getWeddingUniqueCode().isBlank()) {
                // Create Fresh WeddingObject
                newWeddingObject.setWeddingUniqueCode(UUID.randomUUID().toString());
                weddingHost.getCreatedWeddingSet().add(newWeddingObject);
                return this.weddingHostRepo.save(weddingHost);
            } else {
                // else Check code already exist or not
                try {
                    weddingService.getWeddingForGivenWeddingCode(newWeddingObject.getWeddingUniqueCode());
                    // If code exists
                    throw new WeddingObjectAlreadyExistsException("Wedding Object with unique code : %s already exits and have one Host".formatted(newWeddingObject.getWeddingUniqueCode()));

                }
                // Create Fresh WeddingObject
                catch (WeddingObjectDoesNotExistsException e) {
                    newWeddingObject.setWeddingUniqueCode(UUID.randomUUID().toString());
                    weddingHost.getCreatedWeddingSet().add(newWeddingObject);
                    return this.weddingHostRepo.save(weddingHost);
                }
            }

        }
        // this should never happen that Wedding Object ID present
        else {
            weddingService.getWeddingByWeddingId(newWeddingObject.getWeddingId());
            throw new WeddingObjectAlreadyExistsException("Wedding Object Already exists and surely have one Host(parent) with Wedding Object Id " + newWeddingObject.getWeddingId());
        }
    }

    /**
     * <p>
     * Updates the existing {@link WeddingHost} Entity <br>
     * <b>Caution:</b>  won't be modifying weddingHostId
     * </p>
     * <p>
     * Note:
     *
     * @param updatedWeddingHost
     * @param weddingHostId
     * @return the updated {@link WeddingHost}
     */
    public WeddingHost updateWeddingHost(WeddingHost updatedWeddingHost, Long weddingHostId) {
        WeddingHost olderWeddingHost = this.getWeddingHostById(weddingHostId);

        // update fields if not null
        if (updatedWeddingHost.getWeddingHostName() != null && !updatedWeddingHost.getWeddingHostName().isBlank())
            olderWeddingHost.setWeddingHostName(updatedWeddingHost.getWeddingHostName());

        if (updatedWeddingHost.getWeddingHostEmailId() != null && !updatedWeddingHost.getWeddingHostEmailId().isBlank())
            olderWeddingHost.setWeddingHostEmailId(updatedWeddingHost.getWeddingHostEmailId());

        // for WeddingObject Set we will delete previous WeddingObject and then add the new WeddingObject Set
        if (updatedWeddingHost.getCreatedWeddingSet() != null) {
            olderWeddingHost.getCreatedWeddingSet().forEach(wedding -> wedding.setWeddingHost(null));
            olderWeddingHost.getCreatedWeddingSet().clear();
            olderWeddingHost.setCreatedWeddingSet(updatedWeddingHost.getCreatedWeddingSet());
        }

        return weddingHostRepo.save(olderWeddingHost);

    }

    public boolean deleteWeddingHost(Long weddingHostId) {
        WeddingHost existingWeddingHost = this.getWeddingHostById(weddingHostId);
        if (existingWeddingHost.getCreatedWeddingSet() != null) {
            existingWeddingHost.getCreatedWeddingSet().forEach(wedding -> wedding.setWeddingHost(null));
            existingWeddingHost.getCreatedWeddingSet().clear();
        }
        boolean isEntityDeleted = false;
        try {
            this.weddingHostRepo.delete(existingWeddingHost);
            isEntityDeleted = true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An exception: not able to delete Existing Wedding Host", e);
        }
        return isEntityDeleted;

    }


    public WeddingHost updateWeddingObjectByGivenWeddingHostId(Wedding updatedWeddingObj, Long weddingHostId) {
        WeddingHost existingWeddingHost = this.getWeddingHostById(weddingHostId);
        Wedding existingWeddingObj = existingWeddingHost.getCreatedWeddingSet().stream().filter(wedding -> wedding.getWeddingId() == updatedWeddingObj.getWeddingId()).findFirst().orElseThrow(() -> new WeddingObjectDoesNotExistsException("Wedding Object with this %s wedding Id does not exists".formatted(updatedWeddingObj.getWeddingId())));

        // start updating
        if (updatedWeddingObj.getWeddingTitle() != null && !updatedWeddingObj.getWeddingTitle().isBlank()) {
            existingWeddingObj.setWeddingTitle(updatedWeddingObj.getWeddingTitle());
        }
        if (updatedWeddingObj.getWeddingDateTime() != null) {
            existingWeddingObj.setWeddingDateTime(updatedWeddingObj.getWeddingDateTime());
        }
        if (updatedWeddingObj.getWeddingEventDates() != null) {
            existingWeddingObj.setWeddingEventDates(updatedWeddingObj.getWeddingEventDates());
        }
        //changes the owner of Wedding Object to new Wedding Host
        if (updatedWeddingObj.getWeddingHost() != null) {
            existingWeddingObj.setWeddingHost(updatedWeddingObj.getWeddingHost());
        }

        //Wedding members can independently decide whether to be part of the Wedding object.

        return this.weddingHostRepo.save(existingWeddingHost);
    }
}
