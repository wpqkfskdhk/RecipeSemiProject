package com.icia.recipe.dao;
import com.icia.recipe.dto.Board.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {


    List<CommentEntity> findAllByBoard_BNum(int cbNum);

}
