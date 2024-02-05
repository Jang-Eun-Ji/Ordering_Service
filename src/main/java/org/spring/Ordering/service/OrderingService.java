package org.spring.Ordering.service;

import org.spring.Item.domain.Item;
import org.spring.Item.repository.ItemRepository;
import org.spring.Member.domain.Member;
import org.spring.Member.repository.MemberRepository;
import org.spring.OrderItem.domain.OrderItem;
import org.spring.Ordering.Dto.OrderReqDto;
import org.spring.Ordering.Dto.OrderResDto;
import org.spring.Ordering.domain.OrderStatus;
import org.spring.Ordering.domain.Ordering;
import org.spring.Ordering.repository.OrderingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    public Ordering orderingCreate(OrderReqDto orderReqDto)throws IllegalArgumentException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("not fount item"));
        Ordering ordering = Ordering.builder().member(member).build();
        for (OrderReqDto.OrderReqItemDto dto: orderReqDto.getOrderReqItemDtos()){
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

    public Ordering orderingCancel(Long id) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
//        order 구현 뒤 order member의 email과 비교

        Ordering ordering = orderingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found ordering"));
        if(!ordering.getMember().getEmail().equals(email) && !authentication.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN")))){
            throw new AccessDeniedException("권한이 없습니다.");
        }
//      취소된 주문인지 확인
        if(ordering.getOrderStatus() == OrderStatus.CANCELED){
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }
        ordering.cancleOrder();
        for (OrderItem orderItem : ordering.getOrderItems()){
            orderItem.getItem().updateStockQuantity(
                    orderItem.getItem().getStockQuantity() + orderItem.getQuantity());
        }
        return ordering;
    }

    public List<OrderResDto> findAll() {
        List<Ordering> orderings = orderingRepository.findAll();
        return orderings.stream().map(o -> OrderResDto.toDto(o)).collect(Collectors.toList());
    }

    public List<OrderResDto> findByMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
        List<Ordering> orderings = member.getOrderings();
        return orderings.stream().map(o->OrderResDto.toDto(o)).collect(Collectors.toList());

    }

    public List<OrderResDto> findMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException());
        List<Ordering> orderings = member.getOrderings();
        return orderings.stream().map(o->OrderResDto.toDto(o)).collect(Collectors.toList());
    }
}
