package com.icia.recipe.dto.Board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class BoardDTO {

    private int BNum;                   // 게시글 번호
    private String BWriter;             // 게시글 작성자
    private String BTitle;              // 게시글 제목
    private String BContent;            // 게시글 내용
    private LocalDateTime BDate;        // 작성일
    private LocalDateTime BUpdateDate;  //
    private int BHit;                   // 조회수
    private MultipartFile BFile;        //
    private String BFileName;           // 파일이름

    public static BoardDTO toDTO(BoardEntity entity){
        BoardDTO dto = new BoardDTO();

        dto.setBNum(entity.getBNum());
        dto.setBTitle(entity.getBTitle());
        dto.setBContent(entity.getBContent());
        dto.setBDate(entity.getBDate());
        dto.setBUpdateDate(entity.getBUpdateDate());
        dto.setBHit(entity.getBHit());
        dto.setBFileName(entity.getBFileName());

        dto.setBWriter(entity.getMember().getMId());

        return dto;
    }

}
