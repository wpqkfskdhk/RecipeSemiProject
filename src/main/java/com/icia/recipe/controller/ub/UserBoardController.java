package com.icia.recipe.controller.ub;

import com.icia.recipe.dto.Board.UserBoardDTO;
import com.icia.recipe.service.ub.UserBoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserBoardController {

    private final UserBoardService ubsvc;

    private final HttpSession session;

    // blog Post
    @GetMapping("/blogpost")
    public String blogPost(){
        return "/blog-post";
    }

    // 글쓰기 페이지로 이동
    @GetMapping("/blog-write")
    public String goToBlogWritePage() {
        return "blog-write"; // blog-write.html 페이지 반환
    }

    // 글 작성 처리
    @PostMapping("/blogWrite")
    public ModelAndView writePost(@ModelAttribute UserBoardDTO uboard) {
        return ubsvc.writePost(uboard); // 글 작성 후 게시글 목록 페이지로 리디렉션
    }

    // bView : 게시글 상세보기
    @GetMapping("/uBView/{UBNum}")
    public ModelAndView uBView(@PathVariable int UBNum){
        return ubsvc.uBView(UBNum);
    }

    // bModify : 게시글 수정
    @PostMapping("/uBModify")
    public ModelAndView uBModify(@ModelAttribute UserBoardDTO uboard){
        System.out.println("\n게시글 수정 메소드\n[1]html → controller : " + uboard);
        return ubsvc.uBModify(uboard);
    }
    // bModify : 게시글 삭제
    @GetMapping("/uBDelete")
    public ModelAndView uBDelete(@ModelAttribute UserBoardDTO uboard){
        System.out.println("\n게시글 삭제 메소드\n[1]html → controller : " + uboard);
        return ubsvc.uBDelete(uboard);
    }

}
