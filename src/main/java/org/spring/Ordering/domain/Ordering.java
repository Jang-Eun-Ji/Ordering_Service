package org.spring.Ordering.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.Member.domain.Member;
import org.spring.OrderItem.domain.OrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Ordering {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //LAZY 지연, EAGER: 즉시로딩
    @JoinColumn( nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

    @Builder // allarg에 builder 붙이면 안의 객체를 다 정의하지 않으면 null로 값이 설정됨/ 기본값을 설정해도 null임 (builder default 는 기본값 설정됨)
    public Ordering(Member member){
        this.member = member;
    }


    public void cancleOrder(){
        this.orderStatus = OrderStatus.CANCELED;
    }

}
