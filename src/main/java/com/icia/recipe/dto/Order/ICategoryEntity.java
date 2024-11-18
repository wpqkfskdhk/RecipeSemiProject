package com.icia.recipe.dto.Order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="ICTABLE")
public class ICategoryEntity {

    @Id
    private int ICcode;       // 카테고리 코드

    @Column
    private String ICname;    // 카테고리 이름

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

    public static ICategoryEntity toEntity(ICategoryDTO dto){
        ICategoryEntity entity = new ICategoryEntity();
        entity.setICcode(dto.getICcode());
        entity.setICname(dto.getICname());
        return entity;
    }
}