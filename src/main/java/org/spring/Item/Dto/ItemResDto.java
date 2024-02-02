package org.spring.Item.Dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.xml.transform.sax.SAXResult;
@Data
@Builder
@AllArgsConstructor
public class ItemResDto {
    private Long id;
    private String name;
    private String category;
    private int price;
    private int stockQuantity;
    private String imagePath;
}
