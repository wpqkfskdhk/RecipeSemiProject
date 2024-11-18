package com.icia.recipe.controller.order;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequiredArgsConstructor
public class OrderController {
    private HttpSession session;

    @GetMapping("/order")
    public String order() {
        return "order";
    }
    @GetMapping("/orderComplete")
    public String orderComplete() {
        return "orderComplete";
    }


}
