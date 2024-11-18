package com.icia.recipe.controller.product;

import com.icia.recipe.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService psvc;

    @GetMapping("/product")
    public String product() {
        return "product";
    }



}
