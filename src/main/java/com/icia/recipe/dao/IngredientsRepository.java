package com.icia.recipe.dao;

import com.icia.recipe.dto.Ingredients.IngredientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Integer> {
}
