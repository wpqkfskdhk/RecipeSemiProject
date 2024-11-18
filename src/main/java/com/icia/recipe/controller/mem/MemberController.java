package com.icia.recipe.controller.mem;

import com.icia.recipe.dto.Member.MemberDTO;
import com.icia.recipe.service.mem.MemberService;
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
public class MemberController {


    private final MemberService msvc;

    private final HttpSession session;

    // index : 처음페이지로 이동
    @GetMapping("/index")
    public String index(){
        return "index";
    }


    // mJoinForm : 회원가입 페이지로 이동
    @GetMapping("/mJoinForm")
    public String mJoinForm(){
        return "join";
    }

    // mLoginForm : 로그인 페이지로 이동
    @GetMapping("/mLoginForm")
    public String mLoginForm(){
        return "login";
    }

    // mJoin : 회원가입
    @PostMapping("/mJoin")
    public ModelAndView mJoin(@ModelAttribute MemberDTO member){
        return msvc.mJoin(member);
    }

    // mLogin : 로그인
    @PostMapping("/mLogin")
    public ModelAndView mLogin(@ModelAttribute MemberDTO member){
        return msvc.mLogin(member);
    }

    // mLogout :로그아웃
    @GetMapping("/mLogout")
    public String mLogout(){
        session.invalidate();
        return "index";
    }

   @GetMapping("/mView")
    public String view(){
        return "mView";
   }
   @GetMapping("/list")
    public String list(){
        return "list";
   }
    // mList : 회원목록
    @GetMapping("/mList")
    public ModelAndView mList(){
        System.out.println("\n회원목록 메소드\n[1] html → controller");
        return msvc.mList();
    }

    // mView : 회원 상세보기
    @GetMapping("/mView/{mId}")
    public ModelAndView mView(@PathVariable String mId){
        System.out.println("\n회원상세보기 메소드\n[1] html → controller : " + mId);
        return msvc.mView(mId);
    }

    // mModify : 회원 수정
    @PostMapping("/mModify")
    public ModelAndView mModify(@ModelAttribute MemberDTO member){
        System.out.println("\n회원수정 메소드\n[1] html → controller : " + member);
        return msvc.mModify(member);
    }

    // mDelete : 회원삭제
    @GetMapping("/mDelete")
    public ModelAndView mDelete(@ModelAttribute MemberDTO member){
        System.out.println("\n회원상세보기 메소드\n[1] html → controller : " + member);
        return msvc.mDelete(member);
    }
}

















