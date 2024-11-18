package com.icia.recipe.dao;

import com.icia.recipe.dto.Order.ICategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<ICategoryEntity, Integer> {
}
