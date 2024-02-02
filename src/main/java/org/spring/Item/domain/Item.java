package org.spring.Item.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private int price;

    private int stockQuantity;

    private String imagePath;
    @Builder.Default
    private String delYn = "N";

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

    public void deleteItem(){
        this.delYn = "Y";
    }
    public void updateStockQuantity(int newQuantity){
        this.stockQuantity = newQuantity;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public void updateItem(String name, String category, int price, int stockQuantity, String imagePath){
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imagePath = imagePath;
    }
}
