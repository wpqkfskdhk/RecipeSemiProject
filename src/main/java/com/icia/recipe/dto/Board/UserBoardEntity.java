package com.icia.recipe.dto.Board;

import com.icia.recipe.dto.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "UBTABLE")
@SequenceGenerator(name = "UBE_SEQ_GENERATOR", sequenceName = "UBE_SEQ", allocationSize = 1)
public class UserBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UBE_SEQ_GENERATOR")
    private int UBNum;                   // 게시글 번호

    @Column
    private String UBTitle;              // 게시글 제목

    @Column(length = 1000)
    private String UBContent;            // 게시글 내용

    @Column(updatable = false )
    @CreationTimestamp
    private LocalDateTime UBDate;        // 작성일

    @Column(insertable = false)
    @CreationTimestamp
    private LocalDateTime UBUpdateDate;  // 업데이트

    @Column
    private int UBHit;                   // 조회수

    @Column
    private String UBFileName;           // 파일이름

    @ManyToOne
    @JoinColumn(name = "UBWriter")
    private MemberEntity member;     // 게시글 작성자


    public static UserBoardEntity toEntity(UserBoardDTO dto){
        UserBoardEntity entity = new UserBoardEntity();
        MemberEntity member = new MemberEntity();

        entity.setUBNum(dto.getUBNum());
        entity.setUBTitle(dto.getUBTitle());
        entity.setUBContent(dto.getUBContent());
        entity.setUBDate(dto.getUBDate());
        entity.setUBUpdateDate(dto.getUBUpdateDate());
        entity.setUBHit(dto.getUBHit());
        entity.setUBFileName(dto.getUBFileName());

        member.setMId(dto.getUBWriter());
        entity.setMember(member);

        return entity;
    }
}
