package org.spring.Item.repository;

import org.spring.Item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Specification<Item> specification, Pageable pageable);
//  Page<Item> findAllByDelYnAndCategoryLikeOrNameLike(String delYn, String category, String name, Pageable pageable);

}
