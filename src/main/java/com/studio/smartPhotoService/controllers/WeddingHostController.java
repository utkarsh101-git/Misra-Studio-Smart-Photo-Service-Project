package com.studio.smartPhotoService.controllers;

import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingHost;
import com.studio.smartPhotoService.services.WeddingHostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    This controller handles all request for CRUD related operations for Wedding Host
 */
@RestController
@RequestMapping("/wedding-host")
public class WeddingHostController {

    @Autowired
    private WeddingHostService weddingHostService;

    /*
    handles request for creating a new WeddingHost
    delegates the request to WeddingHostService
     */
    @PostMapping(path="/create-new-host" ,consumes = "application/json", produces = "application/json")
    public ResponseEntity<WeddingHost> createWeddingHost(@Valid @RequestBody WeddingHost weddingHost){
        WeddingHost fetechedWeddingHost =  weddingHostService.createWeddingHost(weddingHost);
        return ResponseEntity.status(HttpStatus.CREATED).body(fetechedWeddingHost);
    }

    /*
    handles request for fetching a WeddingHost for given ID
    delegates the request to WeddingHostService
     */
    @GetMapping("/get-host-byId/{weddingHostId}")
    public ResponseEntity<WeddingHost> getWeddingHostById(@PathVariable("weddingHostId") Long weddingHostId){
        WeddingHost fetechedWeddingHost = weddingHostService.getWeddingHostById(weddingHostId);
        return ResponseEntity.status(HttpStatus.OK).body(fetechedWeddingHost);
    }

    /*
     * Handles request for adding a new Wedding Object by same Wedding Host
     * delegates the request to WeddingHostService
     */
    @PutMapping("/add-new-wedding/{weddingHostId}")
    public ResponseEntity<WeddingHost> addNewWeddingForWeddingHost(@Valid @RequestBody Wedding wedding, @PathVariable("weddingHostId") Long weddingHostId){
        return ResponseEntity.status(HttpStatus.OK).body(this.weddingHostService.addNewWeddingObjectToWeddingHost(wedding, weddingHostId));

    }

}
