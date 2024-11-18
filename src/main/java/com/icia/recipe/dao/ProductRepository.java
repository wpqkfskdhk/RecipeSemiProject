package com.icia.recipe.dao;


import com.icia.recipe.dto.Order.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    List<ProductEntity> findByPNameContainingOrderByPNumDesc(String keyword);

    List<ProductEntity> findByPCategoryContainingOrderByPNumDesc(String keyword);


    List<ProductEntity> findByPNameAndPCategory(String kName, String kitchen);

    List<ProductEntity> findByIngredientIName(String ingredients);


    Optional<ProductEntity> findByPNum(int pNum);
}
