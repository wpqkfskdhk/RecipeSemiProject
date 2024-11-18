package com.icia.recipe.controller.board;


import com.icia.recipe.dto.Board.BoardDTO;
import com.icia.recipe.service.board.BoardService;

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
public class BoardController {

    private final BoardService bsvc;

    private final HttpSession session;

    @GetMapping("/bWriteForm")
    public String bWriteForm(){
        return "write";
    }
    // bWrite : 게시글 작성
    @PostMapping("/bWrite")
    public ModelAndView bWrite(@ModelAttribute BoardDTO board){
        System.out.println("\n게시글작성\n[1] board : " + board);
        return bsvc.bWrite(board);
    }

    // bList : 게시글 목록 페이지로 이동
    @GetMapping("/bList")
    public String bList(){
        return "/list";
    }

    // bView : 게시글 상세보기
    @GetMapping("/bView/{BNum}")
    public ModelAndView bView(@PathVariable int BNum){
        return bsvc.bView(BNum);
    }

    // bModify : 게시글 수정
    @PostMapping("/bModify")
    public ModelAndView bModify(@ModelAttribute BoardDTO board){
        System.out.println("\n게시글 수정 메소드\n[1]html → controller : " + board);
        return bsvc.bModify(board);
    }
    // bModify : 게시글 삭제
    @GetMapping("/bDelete")
    public ModelAndView bDelete(@ModelAttribute BoardDTO board){
        System.out.println("\n게시글 삭제 메소드\n[1]html → controller : " + board);
        return bsvc.bDelete(board);
    }


    @PostMapping("/rList")
    public String blList(){
        return "/blog-post";
    }


}
