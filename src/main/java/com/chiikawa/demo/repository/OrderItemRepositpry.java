package com.chiikawa.demo.repository;

import com.chiikawa.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepositpry extends JpaRepository<Order, Long > {
}
