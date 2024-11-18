package com.icia.recipe.controller;

import com.icia.recipe.dto.Board.BoardDTO;
import com.icia.recipe.dto.Board.UserBoardDTO;
import com.icia.recipe.dto.Order.*;
import com.icia.recipe.dto.SearchDTO;

import com.icia.recipe.service.board.BoardService;
import com.icia.recipe.service.cart.CartService;
import com.icia.recipe.service.mem.MemberService;
import com.icia.recipe.service.order.OrderService;
import com.icia.recipe.service.product.ProductService;
import com.icia.recipe.service.recipe.RecipeService;
import com.icia.recipe.service.ub.UserBoardService;
import com.icia.recipe.service.wish.WishService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestfulController {

    private final MemberService msvc;
    private final ProductService psvc;
    private final CartService csvc;
    private final OrderService osvc;
    private final HttpSession session;
    private final BoardService bsvc;
    private final UserBoardService ubsvc;
    private final RecipeService rcpsvc;
    private final WishService wsvc;

    // idCheck : 아이디 중복 체크
    @PostMapping("/idCheck")
    public String idCheck(@RequestParam("MId") String mId) {
        return msvc.idCheck(mId);
    }

    // emailCheck : 이메일 인증번호 받아오기
    @PostMapping("/emailCheck")
    public String emailCheck(@RequestParam("MEmail") String mEmail) {
        return msvc.emailCheck(mEmail);
    }

    // addProduct : 상품 추가하기
    @PostMapping("/addProduct")
    public List<ProductDTO> addProduct() {
        return psvc.addProduct();
    }

    // searchList : 상품 검색 목록 가져오기
    @PostMapping("/searchList")
    public List<ProductDTO> searchList(@ModelAttribute SearchDTO search) {
        System.out.println("\n게시글 검색 메소드\n[1]html->controller :" + search);
        return psvc.searchList(search);
    }

    // cartList : 장바구니 목록 가져오기
    @GetMapping("/cartList")
    public List<CartDTO> cartList() {
        return csvc.cartList();
    }

    // wishList : 위시리스트 가져오기
    @PostMapping("/WishList")
    public List<WishDTO> wishList() {
        return wsvc.wish();
    }

    // addCart : 장바구니에 상품 추가
    @PostMapping("/addCart/{PNum}")
    public ModelAndView addCart(@PathVariable int PNum) {
        return csvc.addCart(PNum);
    }

    // Cart : 장바구니 상품 삭제
    @PostMapping("/cart/{CNum}")
    public ModelAndView Cart(@PathVariable int CNum) {
        return csvc.removeCartItem(CNum);
    }

    // deleteCart : 장바구니 비우기
    @PostMapping("/deleteCart")
    public ModelAndView deleteCart(@ModelAttribute CartDTO cart) {
        return csvc.deleteCart(cart);
    }

    // orderDetail : 주문 내역 가져오기
    @PostMapping("/orderDetail")
    public List<OrderDTO> orderDetail() {
        return osvc.orderhistory();
    }

    // completePay : 결제 완료 후 장바구니 비우기
    @PostMapping("/completePay")
    public ModelAndView completePay(@ModelAttribute CartDTO cart) {
        session.removeAttribute("cartItems");
        return csvc.deleteCart(cart);
    }

    // updateQuantity : 장바구니 상품 수량 변경
    @PostMapping("/updateQuantity")
    public ModelAndView updateQuantity(@RequestParam("CNum") int CNum,
                                       @RequestParam("Quantity") int Quantity) {
        return csvc.updateQuantity(CNum, Quantity);
    }

    // boardList : 게시판 목록 가져오기
    @PostMapping("/boardList")
    public List<BoardDTO> bList() {
        return bsvc.boardList();
    }

    // blog-post : 블로그 게시글 목록 가져오기
    @PostMapping("/blog-post")
    public List<BoardDTO> boardList() {
        return bsvc.boardList();
    }

    // bsearchList : 게시글 검색 목록 가져오기
    @PostMapping("/bsearchList")
    public List<BoardDTO> bsearchList(@ModelAttribute SearchDTO search) {
        System.out.println("search : " + search); // 검색 조건 출력
        return bsvc.searchList(search);
    }

    // rsearchList : 레시피 검색 목록 가져오기
    @PostMapping("/rsearchList")
    public List<RecipeDTO> rsearchList(@ModelAttribute SearchDTO search) {
        System.out.println("search : " + search); // 검색 조건 출력
        return rcpsvc.rsearchList(search);
    }

    // blogpostList : 사용자 게시판 목록 가져오기
    @PostMapping("/blogpostList")
    public List<UserBoardDTO> uboardList() {
        return ubsvc.uBoardList();
    }

    // uBsearchList : 사용자 게시글 검색 목록 가져오기
    @PostMapping("/uBsearchList")
    public List<UserBoardDTO> uBsearchList(@ModelAttribute SearchDTO search) {
        System.out.println("search : " + search); // 검색 조건 출력
        return ubsvc.usersearchList(search);
    }
}