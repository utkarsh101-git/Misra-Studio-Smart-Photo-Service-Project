package com.studio.smartPhotoService.controllers;

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
@RequestMapping("/smart-photo-service")
public class WeddingHostController {

    @Autowired
    private WeddingHostService weddingHostService;

    /*
    handles request for creating a new WeddingHost
    delegates the request to WeddingHostService
     */
    @PostMapping("/create-wedding-host")
    public ResponseEntity<WeddingHost> createWeddingHost(@Valid @RequestBody WeddingHost weddingHost){
        WeddingHost fetechedWeddingHost =  weddingHostService.createWeddingHost(weddingHost);
        return ResponseEntity.status(HttpStatus.CREATED).body(fetechedWeddingHost);
    }


}
