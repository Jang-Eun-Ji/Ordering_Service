package org.spring.Member.service;

import org.spring.Member.Dto.LoginReqDto;
import org.spring.Member.Dto.MemberCreateRequestDto;
import org.spring.Member.Dto.MemberResponseDto;
import org.spring.Member.domain.Address;
import org.spring.Member.domain.Member;
import org.spring.Member.domain.Role;
import org.spring.Member.repository.MemberRepository;
import org.spring.Ordering.domain.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public MemberService (MemberRepository memberRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member memberCreate(MemberCreateRequestDto memberCreateRequestDto){
        memberCreateRequestDto.setPassword(passwordEncoder.encode(memberCreateRequestDto.getPassword()));
        Member member =Member.toEntity(memberCreateRequestDto);
        return memberRepository.save(member);
    }
    public MemberResponseDto findMyInfo(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return MemberResponseDto.toMemberResponseDto(member);
    }

    public List<MemberResponseDto> findAll(){
        List<Member> members = memberRepository.findAll();
        return members.stream().map(m->MemberResponseDto.toMemberResponseDto(m)).collect(Collectors.toList());

    }

    public Member login(LoginReqDto loginReqDto)throws IllegalArgumentException{
//        email존재 여부
        Member member = memberRepository.findByEmail(loginReqDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 이메일입니다."));
//        password일치여부
        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호 불일치");
        }
        return member;
    }

//    public List<MemberResponseDto> members (){
//        List<Member>members = memberRepository.findAll();
//        List<MemberResponseDto> memberResponseDto = new ArrayList<>();
//        for (Member member: members){
//            MemberResponseDto memberResponseDto1 = new MemberResponseDto();
//            memberResponseDto.add(memberResponseDto1);
//        }
//        return memberResponseDto;
//
//    }
    public List<Ordering> orderingList(Long id){
       Optional<Member> member = memberRepository.findById(id);
       List<Ordering> orderings = new ArrayList<>();
        for (Ordering ordering : member.get().getOrderings()) {
            orderings.add(ordering);
        }
        return orderings;
    }

}
