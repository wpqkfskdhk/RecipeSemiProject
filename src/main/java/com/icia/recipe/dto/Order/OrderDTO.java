package com.icia.recipe.dto.Order;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private int ONum;
    private LocalDateTime ODate;
    private int OAmount;
    private String OStats;
    private String OMId;
    private String OAddr;
    private String OPhone;
    private String OReceiver;
    private int Quantity;



    public static OrderDTO toDTO(OrderEntity entity) {
        OrderDTO dto = new OrderDTO();

        dto.setONum(entity.getONum());
        dto.setODate(entity.getODate());
        dto.setOAmount(entity.getOAmount());
        dto.setOStats(entity.getOStats());
        dto.setQuantity(entity.getQuantity());
        dto.setOReceiver(entity.getMember().getMName());
        dto.setOMId(entity.getMember().getMId());
        dto.setOAddr(entity.getMember().getMAddr());
        dto.setOPhone(entity.getMember().getMPhone());
        return dto;

    }

}
