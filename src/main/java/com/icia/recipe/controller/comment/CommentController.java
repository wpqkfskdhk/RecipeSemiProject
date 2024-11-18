package com.icia.recipe.controller.comment;

import com.icia.recipe.dto.Board.CommentDTO;
import com.icia.recipe.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService csvc;

    @PostMapping("/cList")
    public List<CommentDTO> cList(@RequestParam("CBNum") int CBNum){
        System.out.println("\n댓글 목록\n[1]html → controller : " + CBNum);
        return csvc.cList(CBNum);
    }

    // cWrite, cModify
    @PostMapping("/cWrite")
    public List<CommentDTO> cWrite(@ModelAttribute CommentDTO comment){
        System.out.println("\n댓글 작성\n[1]html → controller : " + comment);
        return csvc.cWrite(comment);
    }

    // cDelete
    @PostMapping("/cDelete")
    public List<CommentDTO> cDelete(@ModelAttribute CommentDTO comment){
        System.out.println("\n댓글 삭제\n[1]html → controller : " + comment);
        return csvc.cDelete(comment);
    }



}










