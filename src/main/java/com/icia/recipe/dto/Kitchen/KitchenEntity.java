package com.icia.recipe.dto.Kitchen;

import com.icia.recipe.dto.Order.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "KITCHEN")
public class KitchenEntity {

    @Id
    private int KNum;

    @Column
    private String KName;

    @Column
    private String KBrand;

    @Column
    private int KPrice;

    // ProductEntity와의 일대다 관계 설정
    @OneToMany(mappedBy = "kitchen")
    private List<ProductEntity> products;

    public KitchenDTO toDTO() {
        KitchenDTO dto = new KitchenDTO();
        dto.setKNum(this.getKNum());
        dto.setKName(this.getKName());
        dto.setKBrand(this.getKBrand());
        dto.setKPrice(this.getKPrice());
        return dto;
    }
}