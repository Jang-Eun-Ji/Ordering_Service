package org.spring.Ordering.service;

import org.spring.Member.Dto.MemberResponseDto;
import org.spring.Member.domain.Member;
import org.spring.Ordering.Dto.OrderingRequestDto;
import org.spring.Ordering.Dto.OrderingResponseDto;
import org.spring.Ordering.domain.Ordering;
import org.spring.Ordering.repository.OrderingRepository;
import org.spring.Ordering.domain.Ordering;
import org.spring.OrderingApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderingService {
    private final OrderingRepository orderingRepository;
    @Autowired
    public OrderingService(OrderingRepository orderingRepository){
        this.orderingRepository = orderingRepository;
    }

    public void orderingCreate(OrderingRequestDto orderingRequestDto)throws IllegalArgumentException{
        Member member1 = orderingRepository.findById(orderingRequestDto.getMemberId()).get().getMember();
        Ordering ordering = Ordering.builder()
                .member(member1)
                .build();
        orderingRepository.save(ordering);
        System.out.println(ordering);
    }
//    public void orderList(){
//        List<Ordering>orders = orderingRepository.findAll();
//        List<OrderingResponseDto> orderingResponseDtos = new ArrayList<>();
//        for (Ordering ordering: orders){
//            OrderingResponseDto orderingResponseDto1 = new OrderingResponseDto();
//
//            orderingResponseDto1.setOrderStatus(String.valueOf(ordering.getOrderStatus()));
//            orderingResponseDto1.setItem(ordering.get());
//            orderingResponseDto1.setMember(ordering.getAddress());
//            orderingResponseDto1.add(orderingResponseDto1);
//        }
//        return memberResponseDto;
//    }

}
