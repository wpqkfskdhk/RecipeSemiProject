package com.icia.recipe.dto.Order;

import com.icia.recipe.dto.Ingredients.IngredientsEntity;
import com.icia.recipe.dto.Kitchen.KitchenEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Data
@Entity
@Table(name = "PTABLE")
public class ProductEntity {

    @Id
    private int PNum;

    @Column
    private String PName;

    @Column
    private int PPrice;

    @Column
    private String PCategory;

    @Column
    private String PDescription;

    @Column
    private int PStock;

    // ICategoryEntity와의 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "ICcode")  // 외래키 설정
    private ICategoryEntity category;

    @OneToMany(mappedBy = "product")
    private List<CartEntity> carts;

    // IngredientsEntity와의 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "IPNum")  // 외래키 설정
    private IngredientsEntity ingredient;

    // KitchenEntity와의 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "KPNum")  // 외래키 설정
    private KitchenEntity kitchen;

    public static ProductEntity toEntity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();

        // DTO에서 값을 가져와 엔티티에 설정
        entity.setPNum(dto.getPNum());
        entity.setPName(dto.getPName());
        entity.setPPrice(dto.getPPrice());
        entity.setPDescription(dto.getPDescription());
        entity.setPStock(dto.getPStock());

        // 각 관계 설정
        ICategoryEntity category = new ICategoryEntity();
        category.setICcode(dto.getPNum());  // 적절한 외래키 값으로 설정
        entity.setCategory(category);

        IngredientsEntity ingredients = new IngredientsEntity();
        ingredients.setINum(dto.getPNum());  // 적절한 외래키 값으로 설정
        entity.setIngredient(ingredients);

        KitchenEntity kitchen = new KitchenEntity();
        kitchen.setKNum(dto.getPNum());  // 적절한 외래키 값으로 설정
        entity.setKitchen(kitchen);

        return entity;
    }
}
