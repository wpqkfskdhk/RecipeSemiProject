package com.icia.recipe.controller.cart;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class CartController {

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }



}