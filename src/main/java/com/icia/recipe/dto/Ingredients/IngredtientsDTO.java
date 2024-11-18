package com.icia.recipe.dto.Ingredients;

import lombok.Data;

@Data
public class IngredtientsDTO {

    private int INum; //번호
    private String IName; //이름
    private int IQuantity; //재고량
    private int IPrice;

    public static IngredtientsDTO toDTO(IngredientsEntity entity){
        IngredtientsDTO dto  = new IngredtientsDTO();

        dto.setINum(entity.getINum());
        dto.setIName(entity.getIName());
        dto.setIQuantity(entity.getIQuantity());
        dto.setIPrice(entity.getIPrice());
        return dto;
    }
}
