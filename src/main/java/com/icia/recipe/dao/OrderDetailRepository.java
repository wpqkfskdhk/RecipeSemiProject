package com.icia.recipe.dao;

import com.icia.recipe.dto.Order.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, String> {
}
