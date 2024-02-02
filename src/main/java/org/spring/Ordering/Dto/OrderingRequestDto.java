package org.spring.Ordering.Dto;

import lombok.Data;

import java.util.List;
@Data
public class OrderingRequestDto {
    private Long memberId;
    private List<Long> itemId;
    private List<Long> count;
}
