package org.spring.Ordering.service;

import org.spring.Member.domain.Member;
import org.spring.Member.repository.MemberRepository;
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
    @Autowired
    public OrderingService(OrderingRepository orderingRepository, MemberRepository memberRepository){
        this.orderingRepository = orderingRepository;
        this.memberRepository = memberRepository;
    }

    public Ordering orderingCreate(OrderingReqDto orderingReqDto)throws IllegalArgumentException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("not fount item"));
    }

}
