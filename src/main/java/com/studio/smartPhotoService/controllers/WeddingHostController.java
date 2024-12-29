package com.studio.smartPhotoService.controllers;

import com.studio.smartPhotoService.MapperUtils.WeddingHostMapper;
import com.studio.smartPhotoService.entities.Wedding;
import com.studio.smartPhotoService.entities.WeddingHost;
import com.studio.smartPhotoService.services.WeddingHostService;
import com.studio.smartPhotoService.views.WeddingHostResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
    This controller handles all request for CRUD related operations for Wedding Host
 */
@RestController
@RequestMapping("/wedding-host")
public class WeddingHostController {

    @Autowired
    private WeddingHostService weddingHostService;

    /*
    handles request for fetching a WeddingHost for given ID
    delegates the request to WeddingHostService
     */
    @GetMapping("/get-host-byId/{weddingHostId}")
    public ResponseEntity<WeddingHostResponseDTO> getWeddingHostById(@PathVariable("weddingHostId") Long weddingHostId) {
        WeddingHost fetechedWeddingHost = weddingHostService.getWeddingHostById(weddingHostId);
        return ResponseEntity.status(HttpStatus.OK).body(WeddingHostMapper.mapToDTO(fetechedWeddingHost));
    }

    /*
    handles request for fetching all WeddingHost
    delegates the request to WeddingHostService
     */
    @GetMapping("/get-all-host")
    public ResponseEntity<List<WeddingHostResponseDTO>> getAllWeddingHost() {
        return ResponseEntity.status(HttpStatus.OK).body(this.weddingHostService.getAllWeddingHost().stream().map(weddingHost -> WeddingHostMapper.mapToDTO(weddingHost)).collect(Collectors.toList()));
    }


    /*
    handles request for creating a new WeddingHost
    delegates the request to WeddingHostService
     */
    @PostMapping(path = "/create-new-host", consumes = "application/json")
    public ResponseEntity<WeddingHostResponseDTO> createWeddingHost(@Valid @RequestBody WeddingHost weddingHost) {
        WeddingHost fetechedWeddingHost = weddingHostService.createWeddingHost(weddingHost);
        return ResponseEntity.status(HttpStatus.CREATED).body(WeddingHostMapper.mapToDTO(fetechedWeddingHost));
    }


    /*
     * Handles request for adding a new Wedding Object by same Wedding Host
     * delegates the request to WeddingHostService
     */
    @PutMapping("/add-new-wedding/{weddingHostId}")
    public ResponseEntity<WeddingHostResponseDTO> addNewWeddingForWeddingHost(@Valid @RequestBody Wedding wedding, @PathVariable("weddingHostId") Long weddingHostId) {
        return ResponseEntity.status(HttpStatus.OK).body(WeddingHostMapper.mapToDTO(this.weddingHostService.addNewWeddingObjectToWeddingHost(wedding, weddingHostId)));

    }

    /**
     * Updates the details of an existing Wedding Host.
     * <p>
     * This method replaces the provided fields while retaining non-specified information.<br>
     * The {@link Wedding} objects Set is entirely replaced with the newly provided Set.
     * Older {@link Wedding} objects will be deleted, as a {@link Wedding} cannot exist without a Wedding Host.<br>
     * <b>Caution:</b> If a used {@link Wedding} object is removed, it will no longer be accessible.
     * </p>
     *
     * @param weddingHost   the updated wedding host details
     * @param weddingHostId the ID of the wedding host to update
     * @return the updated {@link WeddingHost}
     */

    @PutMapping("/update-wedding-host/{weddingHostId}")
    public ResponseEntity<WeddingHostResponseDTO> updateExistingWeddingHostById(@RequestBody WeddingHost weddingHost, @PathVariable("weddingHostId") Long weddingHostId) {
        return ResponseEntity.status(HttpStatus.OK).body(WeddingHostMapper.mapToDTO(this.weddingHostService.updateWeddingHost(weddingHost, weddingHostId)));

    }

    /**
     * Deletes the the existing Wedding Host.
     * <p>
     * <p>
     * The {@link Wedding} objects Set is entirely removed automatically
     * As a {@link Wedding} cannot exist without a Wedding Host. <br>
     * <b>Caution:</b> If a used {@link Wedding} object is removed, it will no longer be accessible.
     * </p>
     *
     * @param weddingHostId the ID of the wedding host to update
     * @return the updated {@link WeddingHost}
     */

    @DeleteMapping("/delete-wedding-host/{weddingHostId}")
    public ResponseEntity<String> deleteExistingWeddingHostById(@PathVariable("weddingHostId") Long weddingHostId) {
        String message = "Failed to delete Wedding Host with Id :%s".formatted(weddingHostId);
        HttpStatusCode statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        if (this.weddingHostService.deleteWeddingHost(weddingHostId)) {
            message = "Successfully deleted Wedding Host with Id :%s".formatted(weddingHostId);
            statusCode = HttpStatus.OK;
        }
        return ResponseEntity.status(statusCode).body(message);

    }

    @GetMapping("/get-all-weddings-byId/{weddingHostId}")
    public ResponseEntity<Set<Wedding>> getAllWeddingsByHostId(@PathVariable("weddingHostId") Long weddingHostId) {
        return ResponseEntity.status(HttpStatus.OK).body(weddingHostService.getAllWeddingsByHostId(weddingHostId));
    }

    /**
     * Updates Details of single WeddingObject via a WeddingHost
     * <p>
     * This method replaces the provided fields while retaining non-specified information.<br>
     *
     * @param wedding       the Wedding Object to update
     * @param weddingHostId the ID of the wedding host to update
     * @return the updated {@link WeddingHost}
     */

    @PutMapping("/update-wedding/{weddingHostId}")
    public ResponseEntity<WeddingHostResponseDTO> updateExistingWeddingObjectByGivenWeddingHostId(@RequestBody Wedding wedding, @PathVariable("weddingHostId") Long weddingHostId) {
        return ResponseEntity.status(HttpStatus.OK).body(WeddingHostMapper.mapToDTO(this.weddingHostService.updateWeddingObjectByGivenWeddingHostId(wedding, weddingHostId)));

    }

}
