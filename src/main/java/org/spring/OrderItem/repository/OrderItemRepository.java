package org.spring.OrderItem.repository;

import org.spring.Item.domain.Item;
import org.spring.OrderItem.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

//  select p.* from post p left join author a on p.author_id = a.id; - 밑에를 mariDB문으로 바꾸면
//  아래 jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적함
//    @Query("select o from OrderItem o left join o.item") // jpql문
//    List<OrderItem> findAllJoin();
//    //  select p.*, a.* from post p left join author a on p.author_id = a.id; == mariDB문
//    @Query("select o from OrderItem o left join fetch o.item") // jpql문
//    List<OrderItem> findAllFetchJoin();

}
