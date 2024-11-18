package com.icia.recipe.dto.Order;

import com.icia.recipe.dto.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="CTABLE")
@SequenceGenerator(name="CT_SEQ_GENERATOR", sequenceName = "CT_SEQ", allocationSize = 1)
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CT_SEQ_GENERATOR")
    private int CNum;

    @ManyToOne
    @JoinColumn(name="CPNum")
    private ProductEntity product;

    @Column
    private String CPName; // 상품이름
    @Column
    private int CPrice;

    @ManyToOne
    @JoinColumn(name= "CId")
    private MemberEntity member;

    @Column
    private int Quantity;


    public static CartEntity toEntity(CartDTO dto) {
        MemberEntity member = new MemberEntity();
        CartEntity entity = new CartEntity();

        // MemberEntity 조회 후 설정
        member.setMId(dto.getCId()); // CId를 MemberEntity의 MId로 설정
        entity.setMember(member);

        entity.setCNum(dto.getCNum()); // CartEntity의 CNum 설정
        entity.setQuantity(dto.getQuantity()); // 수량 설정

        return entity;
    }
}

