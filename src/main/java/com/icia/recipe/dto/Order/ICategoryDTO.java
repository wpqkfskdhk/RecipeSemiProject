package com.icia.recipe.dto.Order;


import lombok.Data;

@Data
public class ICategoryDTO {

    private int ICcode;       // 카테코리 코드
    private String ICname;   // 카테고리 이름


    public static ICategoryDTO toDTO(ICategoryEntity entity){
        ICategoryDTO dto = new ICategoryDTO();
        dto.setICcode(entity.getICcode());
        dto.setICname(entity.getICname());


        return dto;
    }
}
