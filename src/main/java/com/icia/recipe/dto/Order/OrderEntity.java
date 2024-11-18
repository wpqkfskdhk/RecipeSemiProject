package com.icia.recipe.dto.Order;

import com.icia.recipe.dto.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name="OTABLE")
@SequenceGenerator(name = "OD_SEQ_GENERATOR", sequenceName = "OD_SEQ", allocationSize = 1)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OD_SEQ_GENERATOR")
    private int ONum;

    @Column
    private LocalDateTime ODate;
    @Column
    private int OAmount;
    @Column
    private String OStats;

    @ManyToOne
    @JoinColumn(name="OMId")
    private MemberEntity member;

    @Column
    private String OAddr;
    @Column
    private String OPhone;
    @Column
    private String OReceiver;

    @Column
    private int Quantity;


    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetails;

    public static OrderEntity toEntity(OrderDTO dto){
        OrderEntity entity = new OrderEntity();
        entity.setONum(dto.getONum());
        entity.setODate(dto.getODate());
        entity.setOAmount(dto.getOAmount());
        entity.setOStats(dto.getOStats());
        entity.setOAddr(dto.getOAddr());
        entity.setOPhone(dto.getOPhone());
        entity.setQuantity(dto.getQuantity());
        MemberEntity member = new MemberEntity();
        member.setMName(dto.getOReceiver());
        member.setMId(dto.getOMId());
        member.setMAddr(dto.getOAddr());
        member.setMPhone(dto.getOPhone());

        entity.setMember(member);
        return entity;

    }
}
