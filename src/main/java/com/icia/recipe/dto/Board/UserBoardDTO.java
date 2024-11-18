package com.icia.recipe.dto.Board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class UserBoardDTO {
    private int UBNum;                   // 게시글 번호
    private String UBWriter;             // 게시글 작성자
    private String UBTitle;              // 게시글 제목
    private String UBContent;            // 게시글 내용
    private LocalDateTime UBDate;        // 작성일
    private LocalDateTime UBUpdateDate;  //
    private int UBHit;                   // 조회수
    private MultipartFile UBFile;        //
    private String UBFileName;           // 파일이름

    public static UserBoardDTO toDTO(UserBoardEntity entity){
        UserBoardDTO dto = new UserBoardDTO();

        dto.setUBNum(entity.getUBNum());
        dto.setUBTitle(entity.getUBTitle());
        dto.setUBContent(entity.getUBContent());
        dto.setUBDate(entity.getUBDate());
        dto.setUBUpdateDate(entity.getUBUpdateDate());
        dto.setUBHit(entity.getUBHit());
        dto.setUBFileName(entity.getUBFileName());

        dto.setUBWriter(entity.getMember().getMId());

        return dto;
    }
}
