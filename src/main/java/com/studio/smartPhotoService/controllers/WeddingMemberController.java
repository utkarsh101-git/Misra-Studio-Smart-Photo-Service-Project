package com.studio.smartPhotoService.controllers;

import com.studio.smartPhotoService.MapperUtils.WeddingMemberMapper;
import com.studio.smartPhotoService.entities.WeddingMember;
import com.studio.smartPhotoService.services.WeddingMemberService;
import com.studio.smartPhotoService.views.WeddingMemberDTO;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wedding-member")
public class WeddingMemberController {

    public static final Logger logger = LoggerFactory.getLogger(WeddingMemberController.class);

    @Autowired
    private WeddingMemberService weddingMemberService;

    @PostMapping("create-member")
    public ResponseEntity<WeddingMember> createWeddingMember(@Valid @RequestBody WeddingMemberDTO weddingMemberDTO){
        WeddingMember weddingMember = this.weddingMemberService.createWeddingMember(WeddingMemberMapper.mapToEntity(weddingMemberDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(weddingMember);
    }

    @GetMapping("get-member-byId/{weddingMemberId}")
    public ResponseEntity<WeddingMemberDTO> getWeddingMemberById(@PathVariable("weddingMemberId") Long weddingMemberId){
        logger.info(this.weddingMemberService.getWeddingMemberById(weddingMemberId).toString());
        return ResponseEntity.status(HttpStatus.OK).body( WeddingMemberMapper.mapToDto(
                this.weddingMemberService.getWeddingMemberById(weddingMemberId)
        ));
    }

    @PutMapping("register-to-wedding/memberId/{memberId}/uniqueCode/{uniqueCode}")
    public ResponseEntity<WeddingMemberDTO> registerToWeddingOnGivenUniqueCodeAndMemberId(@PathVariable("memberId") Long memberId, @PathVariable("uniqueCode") String uniqueCode){
        return ResponseEntity.status(HttpStatus.OK).body(
             WeddingMemberMapper.mapToDto(this.weddingMemberService.registerToWeddingObjectByGivenId(memberId, uniqueCode))
        );
    }
}
