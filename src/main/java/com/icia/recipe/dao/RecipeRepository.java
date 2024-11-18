package com.icia.recipe.dao;


import com.icia.recipe.dto.Order.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {
    List<RecipeEntity> findByRTitleContainingOrderByRNumDesc(String keyword);

    List<RecipeEntity> findByRCategoryContainingOrderByRNumDesc(String keyword);

    List<RecipeEntity> findByRDifficultyContainingOrderByRNumDesc(String keyword);
}
