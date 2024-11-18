package com.icia.recipe.dto.Order;

import lombok.Data;


@Data
public class OrderdetailDTO {
    private String ODId ;
    private int OODId;
    private int Quantity ;
    private int  Price ;


    public static OrderdetailDTO toDTO (OrderDetailEntity entity){
        OrderdetailDTO dto = new OrderdetailDTO();

        dto.setODId(entity.getODId());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());

        dto.setOODId(entity.getOrder().getONum());
        return dto;



    }

}

