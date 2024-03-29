package org.spring.Member.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.spring.Member.domain.Address;
import org.spring.Member.domain.Member;

@Getter
@Builder
public class MemberResponseDto {
    private Long id;
    private String name;
    private String  email;
    private String city;
    private String street;
    private String zipcode;
    private int orderCount;

    public static MemberResponseDto toMemberResponseDto(Member member){
        MemberResponseDtoBuilder builder = MemberResponseDto.builder();
        builder.id(member.getId());
        builder.name(member.getName());
        builder.email(member.getEmail());
        builder.orderCount(member.getOrderings().size());
        Address address = member.getAddress();
        if (address != null){
            builder.city(address.getCity());
            builder.street(address.getStreet());
            builder.zipcode(address.getZipcode());
        }
        return builder.build();
    }
}
