package com.icia.recipe.dto.Order;

import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RecipeDTO {

    // 1. 레시피 테이블 - Recipe
    private int RNum;               // 레시피 번호
    private String RTitle;          // 레시피 이름

    private String RIngredient;     // 레시피 재료

    private String RDescription;    // 레시피 설명
    private String RInstructions;   // 레시피 조리방법
    private int RCookTime;          // 레시피 조리시간
    private String RDifficulty;     // 레시피 난이도(상, 중, 하)
    private MultipartFile RImageFile;       // 레시피 이미지파일
    private String RImageFileName;       // 레시피 이미지파일이름
    private String RCategory;       // 레시피 카테고리


    public static RecipeDTO toDTO(RecipeEntity entity){
        RecipeDTO dto = new RecipeDTO();

        dto.setRNum(entity.getRNum());
        dto.setRTitle(entity.getRTitle());

        dto.setRIngredient(entity.getRIngredient());

        dto.setRDescription(entity.getRDescription());
        dto.setRInstructions(entity.getRInstructions());
        dto.setRCookTime(entity.getRCookTime());
        dto.setRDifficulty(entity.getRDifficulty());
        dto.setRImageFileName(entity.getRImageFileName());
        dto.setRCategory(entity.getRCategory());

        return dto;
    }
}
