package com.icia.recipe.service.recipe;

import com.icia.recipe.dao.CartRepository;
import com.icia.recipe.dao.ProductRepository;
import com.icia.recipe.dao.RecipeRepository;
import com.icia.recipe.dto.Order.RecipeDTO;
import com.icia.recipe.dto.Order.RecipeEntity;
import com.icia.recipe.dto.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {


    private final RecipeRepository pcprepo;
    private ModelAndView mav;
    private final ProductRepository prepo;
    private final CartRepository crepo;
    private final RecipeRepository rcpsvc;

    // 레시피 목록 조회
    public List<RecipeDTO> getAllRecipes() {
        // 데이터베이스에서 모든 RecipeEntity 조회
        List<RecipeEntity> rEntity = pcprepo.findAll();

        // RecipeEntity 리스트를 RecipeDTO 리스트로 변환하여 반환
        return rEntity.stream()
                .map(entity -> RecipeDTO.toDTO(entity))  // RecipeDTO의 정적 메서드 사용
                .collect(Collectors.toList());
    }

    // 레시피 상세 조회
    public RecipeDTO recipeDetails(int recipeId) {
        // 레시피 ID를 기반으로 RecipeEntity를 조회
        RecipeEntity rEntity = pcprepo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다"));

        // RecipeEntity를 RecipeDTO로 변환하여 반환
        return RecipeDTO.toDTO(rEntity);
    }

    // 레시피 검색
    public List<RecipeDTO> rsearchList(SearchDTO search) {
        List<RecipeDTO> dtoList = new ArrayList<>();
        List<RecipeEntity> entityList = new ArrayList<>();

        // 검색 조건에 따라 다른 검색 로직 수행
        if (search.getCategory().equals("RTitle")) {
            // 제목으로 검색
            entityList = rcpsvc.findByRTitleContainingOrderByRNumDesc(search.getKeyword());
        } else if (search.getCategory().equals("RCategory")) {
            // 카테고리로 검색
            entityList = rcpsvc.findByRCategoryContainingOrderByRNumDesc(search.getKeyword());
        } else if (search.getCategory().equals("RDifficulty")) {
            // 난이도로 검색
            entityList = rcpsvc.findByRDifficultyContainingOrderByRNumDesc(search.getKeyword());
        }

        // 검색된 RecipeEntity 리스트를 RecipeDTO 리스트로 변환
        for (RecipeEntity entity : entityList) {
            dtoList.add(RecipeDTO.toDTO(entity));
        }

        return dtoList;
    }
}