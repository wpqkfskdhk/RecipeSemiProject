package com.icia.recipe.dto.Order;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class WishDTO {

    private int WNum;
    private LocalDateTime CRDate;
    private String WMId;
    private int WPNum;
    private int WRNum;
    private String WPName;
    private String WRTitle;
    private String WImgUrl;
    private int WPrice;

    public static WishDTO toDTO(WishEntity entity) {
        WishDTO dto = new WishDTO();

        // 기본 필드 설정
        dto.setWNum(entity.getWNum());
        dto.setWMId(entity.getMember().getMId());
        dto.setCRDate(entity.getCRDate());

        // Product와 관련된 필드 설정 (null 확인 후 할당)
        if (entity.getProduct() != null && entity.getProduct().getPNum() != 0 &&
                entity.getProduct().getPName() != null && entity.getProduct().getPPrice() != 0) {
            dto.setWPNum(entity.getProduct().getPNum());
            dto.setWPName(entity.getProduct().getPName());
            dto.setWPrice(entity.getProduct().getPPrice());
        } else {
            dto.setWPNum(0);
            dto.setWPName("x");
            dto.setWPrice(0);
        }

        // Recipe와 관련된 필드 설정 (null 확인 후 할당)
        if (entity.getRecipe() != null && entity.getRecipe().getRNum() != 0 &&
                entity.getRecipe().getRTitle() != null && entity.getRecipe().getRImageFileName() != null) {
            dto.setWRNum(entity.getRecipe().getRNum());
            dto.setWRTitle(entity.getRecipe().getRTitle());
            dto.setWImgUrl(entity.getRecipe().getRImageFileName());
        } else {
            dto.setWRNum(0);
            dto.setWRTitle("x");
            dto.setWImgUrl("x");
        }

        return dto;
    }
}