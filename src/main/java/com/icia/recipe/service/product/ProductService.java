package com.icia.recipe.service.product;

import com.icia.recipe.dao.*;
import com.icia.recipe.dto.Ingredients.IngredientsEntity;
import com.icia.recipe.dto.Kitchen.KitchenEntity;
import com.icia.recipe.dto.Order.*;
import com.icia.recipe.dto.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository prepo;
    private final IngredientsRepository irepo;
    private final KitchenRepository krepo;
    private final ICategoryRepository Icrepo;

    // 상품 데이터 생성 및 반환
    public List<ProductDTO> addProduct() {
        // 데이터베이스에서 필요한 데이터를 조회
        List<IngredientsEntity> ingredients = irepo.findAll(); // 재료 데이터
        List<KitchenEntity> kitchenItems = krepo.findAll();    // 주방용품 데이터
        List<ICategoryEntity> categories = Icrepo.findAll();   // 카테고리 데이터

        List<ProductDTO> products = new ArrayList<>(); // 최종 반환 리스트

        // 카테고리 이름을 카테고리 코드(ICCODE) 기준으로 설정
        String vegetableAndFruitCategoryName = "";
        String cookingToolCategoryName = "";

        for (ICategoryEntity category : categories) {
            if (category.getICcode() == 1) { // ICCODE가 1이면 "채소 및 과일"
                vegetableAndFruitCategoryName = category.getICname();
            } else if (category.getICcode() == 2) { // ICCODE가 2이면 "조리도구"
                cookingToolCategoryName = category.getICname();
            }
        }

        // **재료 데이터 처리**
        for (IngredientsEntity ingredient : ingredients) {
            ProductDTO dto = new ProductDTO();
            dto.setPNum(ingredient.getINum());
            dto.setPName(ingredient.getIName());
            dto.setPPrice(ingredient.getIPrice());
            dto.setPCategory(vegetableAndFruitCategoryName); // 카테고리 설정
            dto.setPDescription("재료 아이템");
            dto.setPStock(ingredient.getIQuantity()); // 재고 설정
            products.add(dto);

            // 중복 확인 후 데이터베이스에 저장
            List<ProductEntity> existingEntities = prepo.findByPNameAndPCategory(ingredient.getIName(), vegetableAndFruitCategoryName);
            if (existingEntities.isEmpty()) {
                ProductEntity productEntity = new ProductEntity();
                productEntity.setPPrice(ingredient.getIPrice());
                productEntity.setPNum(ingredient.getINum());
                productEntity.setPName(ingredient.getIName());
                productEntity.setPCategory(vegetableAndFruitCategoryName);
                productEntity.setPDescription("재료 아이템");
                productEntity.setPStock(ingredient.getIQuantity());
                prepo.save(productEntity);
            }
        }

        // **주방용품 데이터 처리**
        for (KitchenEntity kitchenItem : kitchenItems) {
            ProductDTO dto = new ProductDTO();
            dto.setPNum(kitchenItem.getKNum());
            dto.setPName(kitchenItem.getKName());
            dto.setPPrice(kitchenItem.getKPrice());
            dto.setPCategory(cookingToolCategoryName); // 카테고리 설정
            dto.setPDescription("식기용품 아이템");
            dto.setPStock(1); // 기본 재고 설정
            products.add(dto);

            // 중복 확인 후 데이터베이스에 저장
            List<ProductEntity> existingEntities = prepo.findByPNameAndPCategory(kitchenItem.getKName(), cookingToolCategoryName);
            if (existingEntities.isEmpty()) {
                ProductEntity productEntity = new ProductEntity();
                productEntity.setPNum(kitchenItem.getKNum());
                productEntity.setPName(kitchenItem.getKName());
                productEntity.setPPrice(kitchenItem.getKPrice());
                productEntity.setPCategory(cookingToolCategoryName);
                productEntity.setPDescription("식기용품 아이템");
                productEntity.setPStock(1);
                prepo.save(productEntity);
            }
        }

        return products; // DTO 리스트 반환
    }

    // 상품 검색
    public List<ProductDTO> searchList(SearchDTO search) {
        List<ProductDTO> dtoList = new ArrayList<>();
        List<ProductEntity> entityList = new ArrayList<>();

        // 검색 조건에 따른 조회 로직 분기
        if (search.getCategory().equals("PName")) {
            // 이름으로 검색
            entityList = prepo.findByPNameContainingOrderByPNumDesc(search.getKeyword());
        } else if (search.getCategory().equals("PCategory")) {
            // 카테고리로 검색
            entityList = prepo.findByPCategoryContainingOrderByPNumDesc(search.getKeyword());
        }

        // 조회된 엔티티를 DTO로 변환
        for (ProductEntity entity : entityList) {
            dtoList.add(ProductDTO.toDTO(entity));
        }

        return dtoList; // DTO 리스트 반환
    }
}