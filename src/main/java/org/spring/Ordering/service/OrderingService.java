package org.spring.Ordering.service;

import org.spring.Item.domain.Item;
import org.spring.Item.repository.ItemRepository;
import org.spring.Member.domain.Member;
import org.spring.Member.repository.MemberRepository;
import org.spring.OrderItem.domain.OrderItem;
import org.spring.Ordering.Dto.OrderingReqDto;
import org.spring.Ordering.domain.Ordering;
import org.spring.Ordering.repository.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.annotation.HttpConstraint;
import javax.transaction.Transactional;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public OrderingService(OrderingRepository orderingRepository, MemberRepository memberRepository, ItemRepository itemRepository){
        this.orderingRepository = orderingRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
    }

    public Ordering orderingCreate(OrderingReqDto orderingReqDto)throws IllegalArgumentException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("not fount item"));
        Ordering ordering = Ordering.builder().member(member).build();
        for (OrderingReqDto.OrderReqItemDto dto: orderingReqDto.getOrderReqItemDtos()){
            Item item = itemRepository.findById(dto.getItemId()).orElseThrow(()-> new EntityNotFoundException());
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .quantity(dto.getCount())
                    .ordering(ordering)
                    .build();
            ordering.getOrderItems().add(orderItem);
            if(item.getStockQuantity() - dto.getCount() < 0){
                throw  new IllegalArgumentException("재고가 부족합니다.");
            }
            orderItem.getItem().updateStockQuantity(item.getStockQuantity() - dto.getCount());
        }
        return orderingRepository.save(ordering);

    }

}
