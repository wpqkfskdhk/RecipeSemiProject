package com.icia.recipe.dao;

import com.icia.recipe.dto.Member.MemberEntity;
import com.icia.recipe.dto.Order.ProductEntity;
import com.icia.recipe.dto.Order.RecipeEntity;
import com.icia.recipe.dto.Order.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<WishEntity, Integer> {

    List<WishEntity> findByMember_MId(String wmId);

    Optional<WishEntity> findByMemberAndProduct(MemberEntity member, ProductEntity product);

    Optional<WishEntity> findByMemberAndRecipe(MemberEntity member, RecipeEntity recipe);
}
