package com.icia.recipe.dto.Board;

import com.icia.recipe.dto.Member.MemberEntity;
import com.icia.recipe.dto.Order.RecipeEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "BTABLE")
@SequenceGenerator(name = "BE_SEQ_GENERATOR", sequenceName = "BE_SEQ", allocationSize = 1)
public class BoardEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BE_SEQ_GENERATOR")
    private int BNum;                   // 게시글 번호

    @Column
    private String BTitle;              // 게시글 제목

    @Column(length = 1000)
    private String BContent;            // 게시글 내용

    @Column(updatable = false )
    @CreationTimestamp
    private LocalDateTime BDate;        // 작성일

    @Column(insertable = false)
    @CreationTimestamp
    private LocalDateTime BUpdateDate;  // 업데이트

    @Column
    private int BHit;                   // 조회수
    
    @Column
    private String BFileName;           // 파일이름

    @ManyToOne
    @JoinColumn(name = "BWriter")
    private MemberEntity member;     // 게시글 작성자

    @OneToMany(mappedBy = "board")
    private List<CommentEntity> comments;

    public static BoardEntity toEntity(BoardDTO dto){
        BoardEntity entity = new BoardEntity();
        MemberEntity member = new MemberEntity();

        entity.setBNum(dto.getBNum());
        entity.setBTitle(dto.getBTitle());
        entity.setBContent(dto.getBContent());
        entity.setBDate(dto.getBDate());
        entity.setBUpdateDate(dto.getBUpdateDate());
        entity.setBHit(dto.getBHit());
        entity.setBFileName(dto.getBFileName());

        member.setMId(dto.getBWriter());
        entity.setMember(member);

        return entity;
    }
}
