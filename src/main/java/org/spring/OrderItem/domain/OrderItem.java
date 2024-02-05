package org.spring.OrderItem.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.Item.domain.Item;
import org.spring.Ordering.domain.Ordering;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor //jpa에서 객체를 조립할때 필요함
@Builder
@AllArgsConstructor
public class OrderItem {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Ordering ordering;
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;


//    public void updateOrderingItem(String quantity){
//        this.quantity = quantity;
//
//    }


}
