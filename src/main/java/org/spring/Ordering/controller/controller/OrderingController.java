package org.spring.Ordering.controller.controller;

import org.spring.Ordering.Dto.OrderingRequestDto;
import org.spring.Ordering.Dto.OrderingResponseDto;
import org.spring.Ordering.service.OrderingService;
import org.spring.Ordering.domain.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderingController {
    private final OrderingService orderingService; //방법 2에서는 final을 붙여야함

    @Autowired
    public OrderingController(OrderingService orderingService){
        this.orderingService = orderingService;
    }

    @PostMapping("/order/new")
    public String orderingCreate(@RequestBody OrderingRequestDto orderingRequestDto) {
        orderingService.orderingCreate(orderingRequestDto);
        return "ok";
    }
//    @GetMapping("/orders")
//    public List<OrderingResponseDto> orderList(){
//        orderingService.orderList();
//        return
//    }
}
