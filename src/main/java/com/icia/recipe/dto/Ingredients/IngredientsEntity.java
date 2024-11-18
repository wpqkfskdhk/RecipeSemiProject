package com.icia.recipe.dto.Ingredients;

import com.icia.recipe.dto.Order.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "INGREDIENTS")
public class IngredientsEntity {

    @Id
    private int INum;

    @Column
    private String IName;

    @Column
    private int IQuantity;

    @Column
    private int IPrice;

    // ProductEntity와의 일대다 관계 설정
    @OneToMany(mappedBy = "ingredient")
    private List<ProductEntity> products;

    public static IngredientsEntity toEntity(IngredtientsDTO dto) {
        IngredientsEntity entity = new IngredientsEntity();
        entity.setINum(dto.getINum());
        entity.setIName(dto.getIName());
        entity.setIQuantity(dto.getIQuantity());
        entity.setIPrice(dto.getIPrice());
        return entity;
    }
}