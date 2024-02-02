package org.spring.Ordering.repository;

import org.spring.Item.domain.Item;
import org.spring.OrderItem.domain.OrderItem;
import org.spring.Ordering.domain.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Order;
import java.util.List;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {

//  select p.* from post p left join author a on p.author_id = a.id; - 밑에를 mariDB문으로 바꾸면
//  아래 jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적함
//    @Query("select o from Ordering o left join o.member") // jpql문
//    List<Ordering> findAllJoin();
//    //  select p.*, a.* from post p left join author a on p.author_id = a.id; == mariDB문
//    @Query("select o from Ordering o left join fetch o.member") // jpql문
//    List<Ordering> findAllFetchJoin();
}
