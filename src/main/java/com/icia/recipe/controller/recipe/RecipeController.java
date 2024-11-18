package com.icia.recipe.controller.recipe;
import com.icia.recipe.dto.Order.RecipeDTO;
import com.icia.recipe.service.recipe.RecipeService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService rcpsvc;

    // receipe-post.html : 레시피 이동
    @GetMapping("/receipe-post")
    public String bRecipe(){
        return "receipe-post";
    }

    // recipeList : 모든 레시피 목록 반환
    @GetMapping("/recipeList")
    @ResponseBody  // 반환값을 JSON으로 자동 변환
    public List<RecipeDTO> getRecipeList() {
        return rcpsvc.getAllRecipes();  // RecipeDTO 목록 반환
    }

    // recipe/recipeview.html : 레시피 상세보기페이지 이동
    @GetMapping("/recipe/recipeview")
    public String vRecipe(){
        return "recipe/recipeview";
    }



    // 레시피 상세보기
    @GetMapping("/recipe/details/{recipeId}")
    @ResponseBody
    public RecipeDTO recipeDetails(@PathVariable int recipeId) {
        return rcpsvc.recipeDetails(recipeId); // 레시피 상세 정보를 가져옴
    }

    // 유튜브 링크 JSON 반환
    @GetMapping("/static/youtubelinks.json")
    @ResponseBody
    public String getYoutubeLinks() throws IOException {
        // resources/static 폴더에 있는 youtubeLinks.json 파일 읽기
        ClassPathResource resource = new ClassPathResource("static/youtubelinks.json");
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }

}
