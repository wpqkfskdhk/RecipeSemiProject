package com.icia.recipe.dto.Kitchen;

import lombok.Data;

@Data
public class KitchenDTO {
    private int KNum;
    private String KName;
    private String KBrand;
    private int KPrice;

    public KitchenDTO toDTO(KitchenEntity entity){
        KitchenDTO dto = new KitchenDTO();

        dto.setKNum(entity.getKNum());;
        dto.setKName(entity.getKName());
        dto.setKBrand(entity.getKBrand());
        dto.setKPrice(entity.getKPrice());

        return dto;
    }


}
