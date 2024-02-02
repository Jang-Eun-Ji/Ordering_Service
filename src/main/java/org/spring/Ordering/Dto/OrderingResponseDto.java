package org.spring.Ordering.Dto;

import lombok.Data;
import org.spring.Item.domain.Item;
import org.spring.Member.domain.Member;

@Data
public class OrderingResponseDto {
    Member member;
    String orderStatus;
    Item item;
}
