package com.icia.recipe.dao;

import com.icia.recipe.dto.Order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}
