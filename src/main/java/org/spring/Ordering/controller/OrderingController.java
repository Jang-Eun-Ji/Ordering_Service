package org.spring.Ordering.controller;

import org.spring.Ordering.Dto.OrderReqDto;
import org.spring.Ordering.Dto.OrderResDto;
import org.spring.Ordering.common.CommonResponseDto;
import org.spring.Ordering.domain.Ordering;
import org.spring.Ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderingController {
    private final OrderingService orderingService; //방법 2에서는 final을 붙여야함

    @Autowired
    public OrderingController(OrderingService orderingService){
        this.orderingService = orderingService;
    }

    @PostMapping("/order/create")
    public ResponseEntity<CommonResponseDto> orderingCreate(@RequestBody OrderReqDto orderReqDto) {
        Ordering ordering = orderingService.orderingCreate(orderReqDto);
        return new ResponseEntity<>(new CommonResponseDto
                (HttpStatus.CREATED, "item succesfully create", ordering.getId()),
                HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username") - 사용자나 이메일이같은 사람이면 가능한것
    @DeleteMapping("/order/{id}/cancel")
    public ResponseEntity<CommonResponseDto> orderingCanceled(@PathVariable Long id){
        Ordering ordering = orderingService.orderingCancel(id);
        return new ResponseEntity<>(new CommonResponseDto
                (HttpStatus.OK, "item succesfully canceled", ordering.getId()),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public List<OrderResDto> orderList(){
        return orderingService.findAll();
    }
}
