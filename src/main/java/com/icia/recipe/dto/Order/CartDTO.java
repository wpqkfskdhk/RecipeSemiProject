package com.icia.recipe.dto.Order;


import lombok.Data;

@Data
public class CartDTO {


    private int CNum;       // 카트번호
    private int CPNum;     // 상품번호
    private String CPName; // 상품이름
    private int CPrice;
    private String CId;    // 아이디
    private int Quantity;  // 수량


    public static CartDTO toDTO(CartEntity entity){
        CartDTO dto = new CartDTO();
        dto.setCNum(entity.getCNum());
        dto.setQuantity(entity.getQuantity());
        dto.setCPrice(entity.getProduct().getPPrice());
        dto.setCPName(entity.getProduct().getPName());
        dto.setCId(entity.getMember().getMId());
        dto.setCPNum(entity.getProduct().getPNum());

        return dto;

    }

}
