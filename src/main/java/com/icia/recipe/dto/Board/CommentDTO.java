package com.icia.recipe.dto.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private int CNum;  //댓글 번호

    private int CBNum; //게시글 번호

    private int CUBNum; // 사용자 게시글 번호

    private String CWriter; // 댓글 작성자

    private String CContent; //댓글 내용

    private LocalDateTime CDate; //댓글 작성일

    public static CommentDTO toDTO(CommentEntity entity){
        CommentDTO dto = new CommentDTO();

        dto.setCNum(entity.getCNum());
        dto.setCContent(entity.getCContent());
        dto.setCDate(entity.getCDate());
        dto.setCWriter(entity.getMember().getMId());


        return dto;
    }


}
