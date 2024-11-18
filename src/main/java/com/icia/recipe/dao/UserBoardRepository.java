package com.icia.recipe.dao;

import com.icia.recipe.dto.Board.BoardEntity;
import com.icia.recipe.dto.Board.UserBoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBoardRepository extends JpaRepository<UserBoardEntity,Integer> {

    List<UserBoardEntity> findAllByOrderByUBNumDesc();

    List<UserBoardEntity> findByMember_MIdContainingOrderByUBNumDesc(String keyword);

    List<UserBoardEntity> findByUBTitleContainingOrderByUBNumDesc(String keyword);

    List<UserBoardEntity> findByUBContentContainingOrderByUBNumDesc(String keyword);

    @Modifying
    @Transactional
    @Query("UPDATE UserBoardEntity b Set b.UBHit = b.UBHit + 1 WHERE b.UBNum = :UBNum")
    void incrementUBHit(@Param("UBNum") int UBNum);
}
