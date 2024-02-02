package org.spring.Ordering.controller;

import org.spring.Ordering.Dto.OrderingReqDto;
import org.spring.Ordering.common.CommonResponseDto;
import org.spring.Ordering.domain.Ordering;
import org.spring.Ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderingController {
    private final OrderingService orderingService; //방법 2에서는 final을 붙여야함

    @Autowired
    public OrderingController(OrderingService orderingService){
        this.orderingService = orderingService;
    }

    @PostMapping("/order/create")
    public ResponseEntity<CommonResponseDto> orderingCreate(@RequestBody OrderingReqDto orderingReqDto) {
        Ordering ordering = orderingService.orderingCreate(orderingReqDto);
        return new ResponseEntity<>(new CommonResponseDto
                (HttpStatus.CREATED, "item succesfully create", ordering.getId()),
                HttpStatus.CREATED);
    }
//    @GetMapping("/orders")
//    public List<OrderingResponseDto> orderList(){
//        orderingService.orderList();
//        return
//    }
}
