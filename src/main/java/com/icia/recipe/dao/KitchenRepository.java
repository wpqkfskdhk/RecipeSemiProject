package com.icia.recipe.dao;

import com.icia.recipe.dto.Kitchen.KitchenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenRepository   extends JpaRepository<KitchenEntity, Integer> {
}
