package org.spring.Member.controller;

import org.hibernate.criterion.Order;
import org.spring.Member.Dto.LoginReqDto;
import org.spring.Member.Dto.MemberCreateRequestDto;
import org.spring.Member.Dto.MemberResponseDto;
import org.spring.Member.domain.Member;
import org.spring.Member.service.MemberService;
import org.spring.Ordering.Dto.OrderResDto;
import org.spring.Ordering.common.CommonResponseDto;
import org.spring.Ordering.domain.Ordering;
import org.spring.Ordering.securites.JwtTokenProvide;
import org.spring.Ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvide jwtTokenProvide;
    private final OrderingService orderingService;

    @Autowired
    public MemberController(MemberService memberService, JwtTokenProvide jwtTokenProvide, OrderingService orderingService){
        this.memberService = memberService;
        this.jwtTokenProvide = jwtTokenProvide;
        this.orderingService = orderingService;
    }

    @PostMapping("member/new")
    public ResponseEntity<CommonResponseDto> memberCreate(@Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto) {
        Member member = memberService.memberCreate(memberCreateRequestDto);
        return new ResponseEntity<>(new CommonResponseDto
                (HttpStatus.CREATED, "member successfully created", member.getId()), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("members")
    public List<MemberResponseDto> members(){
        return memberService.findAll();
    }
    @GetMapping("/member/myInfo")
    public MemberResponseDto findmyInfo(){
        return memberService.findMyInfo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/member/{id}/orders")
    public List<OrderResDto> findMemberOrder(@PathVariable Long id){
        return orderingService.findByMember(id);
    }

//    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")// 나중에 사용자랑 이메일 같은거에만 권한주고 싶을때

    @PostMapping("/member/{id}/orders")
    public List<OrderResDto> orderingList(@PathVariable Long id){
        return orderingService.findByMember(id);
    }
    @GetMapping("/member/myorders")
    public List<OrderResDto> findMyOrders(){
        return orderingService.findMyOrders();
    }
    @PostMapping("/doLogin")
    public ResponseEntity<CommonResponseDto> memberLogin(@Valid @RequestBody LoginReqDto loginReqDto){
        Member member = memberService.login(loginReqDto);

//        토큰 생성
        String jwtToken = jwtTokenProvide.createToken(member.getEmail(), member.getRole().toString());
        Map<String, Object>member_info = new HashMap<>();
        member_info.put("id", member.getId());
        member_info.put("token", jwtToken);
        return new ResponseEntity<>(new CommonResponseDto(HttpStatus.OK, "member successfully logined", member_info), HttpStatus.OK);
    }

}
