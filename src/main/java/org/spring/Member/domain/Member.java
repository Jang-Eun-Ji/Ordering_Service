package org.spring.Member.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.Member.Dto.MemberCreateRequestDto;
import org.spring.Ordering.domain.Ordering;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor //jpa에서 객체를 조립할때 필요함
public class Member{

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Embedded
    private Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Ordering> orderings;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

    public static Member toEntity(MemberCreateRequestDto memberCreateRequestDto) {
        Address address = new Address(memberCreateRequestDto.getCity(),
                memberCreateRequestDto.getStreet(),
                memberCreateRequestDto.getZipcode());

        Member member = Member.builder()
                .email(memberCreateRequestDto.getEmail())
                .name(memberCreateRequestDto.getName())
                .password(memberCreateRequestDto.getPassword())
                .address(address)
                .role(Role.USER)
                .build();
        return member;
    }

}
