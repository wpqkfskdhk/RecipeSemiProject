package com.icia.recipe.dto.Order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="ODTABLE")
public class OrderDetailEntity {

    @Id
    private String ODId ;

    @ManyToOne
    @JoinColumn(name="OODId")
    private OrderEntity order;

    @Column
    private int Quantity ;

    @Column
    private int  Price ;


    public OrderDetailEntity toEntity(OrderdetailDTO dto) {
        OrderDetailEntity entity = new OrderDetailEntity();
        OrderEntity order = new OrderEntity();
        entity.setODId(dto.getODId());
        entity.setQuantity(dto.getQuantity());
        order.setONum(dto.getOODId());
        entity.setOrder(order);
        return entity;

    }
}
