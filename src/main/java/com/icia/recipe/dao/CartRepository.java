package com.icia.recipe.dao;

import com.icia.recipe.dto.Order.CartEntity;
import com.icia.recipe.dto.Order.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {




    List<CartEntity> findByMember_MId(String mId);




    List<CartEntity> findByCNum(int cNum);
}
