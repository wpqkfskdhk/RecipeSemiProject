package com.icia.recipe.service.comment;



import com.icia.recipe.dao.CommentRepository;
import com.icia.recipe.dao.UserBoardRepository;
import com.icia.recipe.dto.Board.CommentDTO;
import com.icia.recipe.dto.Board.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository crepo;
    private final UserBoardRepository urepo;

    public List<CommentDTO> cList(int CBNum) {
        List<CommentDTO> dtoList = new ArrayList<>();
        List<CommentEntity> entityList = crepo.findAllByBoard_BNum(CBNum);

        for (CommentEntity entity : entityList) {
            dtoList.add(CommentDTO.toDTO(entity));
        }

        return dtoList;
    }

    public List<CommentDTO> cWrite(CommentDTO comment) {

        // toEntity에서 변환
        CommentEntity entity = CommentEntity.toEntity(comment);

        // 댓글 저장
        crepo.save(entity);

        // 댓글 입력 후 목록 불러오기
        return cList(comment.getCBNum());
    }

    public List<CommentDTO> cDelete(CommentDTO comment) {

        // 댓글 삭제
        crepo.deleteById(comment.getCNum());

        // 댓글 입력 후 목록 불러오기
        List<CommentDTO> dtoList = cList(comment.getCBNum());
        return dtoList;

    }


}
