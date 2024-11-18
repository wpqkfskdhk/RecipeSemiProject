package com.icia.recipe.dto.Order;

import com.icia.recipe.dto.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name="WTABLE")
@SequenceGenerator(name="WT_SEQ_GENERATOR", sequenceName = "WT_SEQ", allocationSize = 1)
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WT_SEQ_GENERATOR")
    private int WNum;

    @ManyToOne
    @JoinColumn(name = "WMId")
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "WRNum")  // 외래 키로 WRNum을 설정
    private RecipeEntity recipe;  // RecipeEntity와의 관계

    @ManyToOne
    @JoinColumn(name = "WPNum")
    private ProductEntity product;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime CRDate;

    @Column
    private String WPName;

    @Column
    private String WRTitle;

    @Column
    private String WImgUrl;

    @Column
    private int WPrice;

    public static WishEntity toEntity(WishDTO dto) {
        WishEntity entity = new WishEntity();

        // MemberEntity 초기화
        MemberEntity member = new MemberEntity();
        member.setMId(dto.getWMId());
        entity.setMember(member);

        // ProductEntity 초기화 및 null 체크
        ProductEntity product = new ProductEntity();
        if (dto.getWPNum() != 0 && dto.getWPName() != null && dto.getWPrice() != 0) {
            product.setPNum(dto.getWPNum());
            product.setPName(dto.getWPName());
            product.setPPrice(dto.getWPrice());
        } else {
            product.setPNum(0);
            product.setPPrice(0);
            product.setPName("x"); // WPNum이 0일 경우 기본값 "x" 설정
        }
        entity.setProduct(product);

        // RecipeEntity 초기화 및 null 체크
        RecipeEntity recipe = new RecipeEntity();
        if (dto.getWRNum() != 0 && dto.getWImgUrl() != null && dto.getWRTitle() != null) {
            recipe.setRNum(dto.getWRNum()); // 실제로 WRNum은 RecipeEntity의 RNum을 참조해야 함
            recipe.setRTitle(dto.getWRTitle()); // WRTitle이 null일 경우 "x" 설정
            recipe.setRImageFileName(dto.getWImgUrl()); // WImgUrl이 null일 경우 "x" 설정
        } else {
            recipe.setRNum(0); // WRNum이 0일 경우 기본값 0 설정
            recipe.setRTitle("x"); // WRTitle이 null일 경우 기본값 "x" 설정
            recipe.setRImageFileName("x"); // WImgUrl이 null일 경우 기본값 "x" 설정
        }

        entity.setRecipe(recipe); // RecipeEntity 설정
        entity.setCRDate(dto.getCRDate());
        entity.setWNum(dto.getWNum());

        return entity;
    }
}