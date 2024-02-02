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

}
