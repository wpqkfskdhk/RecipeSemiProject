package com.icia.recipe.dto.Order;

import lombok.Data;

@Data
public class ProductDTO {
    private int PNum;         // 상품번호
    private String PName;     // 상품 이름
    private int PPrice;    // 상품 가격
    private String PCategory; // 카테고리 이름 (ICname 필드로 매핑)
    private String PDescription; // 상품 설명
    private int PStock;       // 재고량

    public static ProductDTO toDTO(ProductEntity entity){
        ProductDTO dto = new ProductDTO();

        dto.setPNum(entity.getPNum());
        dto.setPName(entity.getPName());
        dto.setPPrice(entity.getPPrice());
        dto.setPCategory(entity.getPCategory());// 카테고리 이름 설정
        dto.setPDescription(entity.getPDescription());
        dto.setPStock(entity.getPStock());
        return dto;
    }
}