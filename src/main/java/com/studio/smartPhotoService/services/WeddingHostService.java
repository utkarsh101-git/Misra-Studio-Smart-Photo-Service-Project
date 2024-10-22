package com.studio.smartPhotoService.services;


import com.studio.smartPhotoService.entities.WeddingHost;
import com.studio.smartPhotoService.exceptions.WeddingHostAlreadyExists;
import com.studio.smartPhotoService.repository.WeddingHostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
This service handles all request delegated from WeddingHostController
Service majorly handles CRUD operation for wedding host

 */
@Service
public class WeddingHostService {

    @Autowired
    public WeddingHostRepo weddingHostRepo;

    /*
    This method finds all email id and wraps them inside a list and returns it
     */
    public List<String> getAllWeddingHostEmails(){
        return  this.weddingHostRepo.findAll().stream()
                .map(WeddingHost::getWeddingHostEmailId).toList();
    }


    /*
    This method creates a wedding host entity in the database with following conditions
        It checks whether the wedding host's email Id is already in use, if yes, then throws Exception
        Also creates a unique id (sharable) for every host entity  and finally persist the entity
     */
    public WeddingHost createWeddingHost(WeddingHost weddingHost){

        Optional<String> optional =  this.getAllWeddingHostEmails().stream().filter(emails -> emails.equals(weddingHost.getWeddingHostEmailId()) ).findAny();
        String foundEmail = optional.orElse(null);
        if(foundEmail != null) {
            throw new WeddingHostAlreadyExists("Wedding Host already exists - email Id of Wedding Host already exists");
        }

        weddingHost.setUniqueId(UUID.randomUUID().toString());
        return this.weddingHostRepo.save(weddingHost);
    }
}
