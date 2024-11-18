package com.icia.recipe.controller.wish;


import com.icia.recipe.service.wish.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class WishController {

    private final WishService wsvc;

    @GetMapping("/wish")
    public String wish() {
        return "WishList";
    }

    //찜목록 추가
    @PostMapping("/addWish/{WPNum}")
    public ModelAndView addWishProduct(@PathVariable("WPNum") int PNum) {
        return wsvc.addWishProduct(PNum);
    }

    //레시피 찜목록 추가
    @PostMapping("/addWish/Recipe/{WRNum}")
    public ModelAndView addWishRecipe(@PathVariable("WRNum") int RNum) {
        return wsvc.addWishrecipe(RNum);
    }

    //찜목록삭제
    @PostMapping("/deleteWish/{WNum}")
    public ModelAndView removeWish(@PathVariable("WNum") int WNum) {
        return wsvc.removeWish(WNum);
    }

}
