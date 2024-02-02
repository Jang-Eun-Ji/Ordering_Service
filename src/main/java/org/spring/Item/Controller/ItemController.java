package org.spring.Item.Controller;

import net.bytebuddy.asm.MemberSubstitution;
import org.spring.Item.Dto.ItemReqDto;
import org.spring.Item.Dto.ItemResDto;
import org.spring.Item.Dto.ItemSearchDto;
import org.spring.Item.Service.ItemService;
import org.spring.Item.domain.Item;
import org.spring.Ordering.common.CommonResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/item/create")
    public ResponseEntity<CommonResponseDto> itemCreate (ItemReqDto itemReqDto){
        Item item = itemService.create(itemReqDto);
        return new ResponseEntity<>(new CommonResponseDto
                (HttpStatus.CREATED, "item succesfully create", item.getId()),
                HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResDto>> items(ItemSearchDto itemSearchDto, Pageable pageable){
        List<ItemResDto> itemResDtos = itemService.findAll(itemSearchDto, pageable);
        return new ResponseEntity<>(itemResDtos, HttpStatus.OK);
    }

    @GetMapping("/item/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long id){
        Resource resource = itemService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/itme/{id}/delete")
    public ResponseEntity<CommonResponseDto> itemDelete(@PathVariable Long id){
        Item item = itemService.delete(id);

        return new ResponseEntity<>(new CommonResponseDto
                (HttpStatus.OK, "item succesfully delete", item.getId()),
                HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/item/{id}/update")
    public ResponseEntity<CommonResponseDto> itemUpdate(@PathVariable Long id, ItemReqDto itemReqDto){
        Item item = itemService.update(id, itemReqDto);
        return new ResponseEntity<>(new CommonResponseDto(HttpStatus.OK, "item successfully updated", item.getId()), HttpStatus.OK);
    }

}
