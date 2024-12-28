package com.studio.smartPhotoService.controllers;

import com.studio.smartPhotoService.MapperUtils.WeddingMemberMapper;
import com.studio.smartPhotoService.entities.WeddingMember;
import com.studio.smartPhotoService.services.WeddingMemberService;
import com.studio.smartPhotoService.views.WeddingMemberDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("wedding-member")
public class WeddingMemberController {

    public static final Logger logger = LoggerFactory.getLogger(WeddingMemberController.class);


    @Autowired
    private WeddingMemberService weddingMemberService;

    @PostMapping("create-member")
    public ResponseEntity<WeddingMember> createWeddingMember(@Valid @RequestBody WeddingMemberDTO weddingMemberDTO) {
        WeddingMember weddingMember = this.weddingMemberService.createWeddingMember(WeddingMemberMapper.mapToEntity(weddingMemberDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(weddingMember);
    }

    @GetMapping("get-member-byId/{weddingMemberId}")
    public ResponseEntity<WeddingMemberDTO> getWeddingMemberById(@PathVariable("weddingMemberId") Long weddingMemberId) {
        logger.info(this.weddingMemberService.getWeddingMemberById(weddingMemberId).toString());
        return ResponseEntity.status(HttpStatus.OK).body(WeddingMemberMapper.mapToDto(
                this.weddingMemberService.getWeddingMemberById(weddingMemberId)
        ));
    }

    @PutMapping("register-to-wedding/memberId/{memberId}/uniqueCode/{uniqueCode}")
    public ResponseEntity<WeddingMemberDTO> registerToWeddingOnGivenUniqueCodeAndMemberId(@PathVariable("memberId") Long memberId, @PathVariable("uniqueCode") String uniqueCode) {
        return ResponseEntity.status(HttpStatus.OK).body(
                WeddingMemberMapper.mapToDto(this.weddingMemberService.registerToWeddingObjectByGivenId(memberId, uniqueCode))
        );
    }


    /*
     * Handling request for adding photos of Wedding member to DB
     */
    @PutMapping("upload-photos-of-member/{memberId}")
    public ResponseEntity<?> uploadPhotosOfWeddingMemberByGivenMemberId(@PathVariable("memberId") Long memberId, @RequestParam("file") List<MultipartFile> files) throws MalformedURLException {

        List<List<String>> filesSuccessAndFailed = this.weddingMemberService.uploadPhotosAndAddPhotosPathToWeddingMember(memberId, files);

        return ResponseEntity.ok("Photos of member with member Id %s Result :\nFiles Not Added : %s\nFiles Added : %s\n ".formatted(memberId, filesSuccessAndFailed.get(1).toString(), filesSuccessAndFailed.get(0).toString()));
    }

    /*
    Handling Request to show what all Images are added by Wedding User
     */


}
