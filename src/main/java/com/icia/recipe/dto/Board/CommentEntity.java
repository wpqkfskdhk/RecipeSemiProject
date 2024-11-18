package com.icia.recipe.dto.Board;
import com.icia.recipe.dto.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "STABLE")
@SequenceGenerator(name = "CMT_SEQ_GENERATOR", sequenceName = "CMT_SEQ", allocationSize = 1)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CMT_SEQ_GENERATOR")
    private int CNum; //댓글 번호 고유 ID

    @Column
    private String CContent; //댓글 내용

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime CDate; //작성일

    @ManyToOne
    @JoinColumn(name = "CWriter")
    private MemberEntity member;


    @ManyToOne
    @JoinColumn(name="CBNum")
    private BoardEntity board;

    public static CommentEntity toEntity(CommentDTO dto){
        CommentEntity entity = new CommentEntity();
        MemberEntity member = new MemberEntity();
        BoardEntity board = new BoardEntity();
        entity.setCNum(dto.getCNum());
        entity.setCContent(dto.getCContent());
        entity.setCDate(dto.getCDate());

        member.setMId(dto.getCWriter());
        entity.setMember(member);

        board.setBNum(dto.getCBNum());
        entity.setBoard(board);


        return entity;
    }




}
