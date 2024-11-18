package com.icia.recipe.dto.Order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "RTABLE")
public class RecipeEntity {

    @Id
    private int RNum;               // 레시피 번호

    @Column
    private String RTitle;          // 레시피 이름

    @Column
    private String RIngredient;     // 레시피 재료

    @Column(length = 1000)
    private String RDescription;    // 레시피 설명

    @Column
    private String RInstructions;   // 레시피 조리방법

    @Column
    private int RCookTime;          // 레시피 조리시간

    @Column
    private String RDifficulty;     // 레시피 난이도(상, 중, 하)

    @Column
    private String RImageFileName;       // 레시피 이미지이름

    @Column
    private String RCategory;       // 레시피 카테고리


  @OneToMany(mappedBy = "recipe")
  private List<WishEntity> wish;

    public static RecipeEntity toEntity(RecipeDTO dto){
        RecipeEntity entity = new RecipeEntity();

        entity.setRNum(dto.getRNum());
        entity.setRTitle(dto.getRTitle());

        entity.setRIngredient(dto.getRIngredient());

        entity.setRDescription(dto.getRDescription());
        entity.setRInstructions(dto.getRInstructions());
        entity.setRCookTime(dto.getRCookTime());
        entity.setRDifficulty(dto.getRDifficulty());
        entity.setRImageFileName(dto.getRImageFileName());
        entity.setRCategory(dto.getRCategory());

        return entity;
    }
}
