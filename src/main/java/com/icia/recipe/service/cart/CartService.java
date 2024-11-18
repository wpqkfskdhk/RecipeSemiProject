package com.icia.recipe.service.cart;

import com.icia.recipe.dao.CartRepository;
import com.icia.recipe.dao.MemberRepository;
import com.icia.recipe.dao.ProductRepository;
import com.icia.recipe.dto.Member.MemberEntity;
import com.icia.recipe.dto.Order.CartDTO;
import com.icia.recipe.dto.Order.CartEntity;
import com.icia.recipe.dto.Order.ProductEntity;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository mrepo;
    private final ProductRepository prepo;
    private final CartRepository crepo;
    private final HttpSession session;
    private ModelAndView mav;

    /**
     * 장바구니 목록 조회
     */
    @Transactional
    public List<CartDTO> cartList() {
        List<CartDTO> dtoList = new ArrayList<>();
        String CId = (String) session.getAttribute("loginId");

        Optional<MemberEntity> entity = mrepo.findById(CId);

        // Optional을 좀 더 깔끔하게 처리
        entity.ifPresentOrElse(member -> {
            List<CartEntity> cartItems = crepo.findByMember_MId(CId);

            // 여러 개의 가격 업데이트 후 한 번만 save() 호출
            for (CartEntity cartItem : cartItems) {
                // 가격이 0이거나 잘못 설정된 경우 ProductEntity에서 가격을 가져와 설정
                if ((cartItem.getCPrice() == 0 || cartItem.getCPrice() != cartItem.getProduct().getPPrice() * cartItem.getQuantity())
                        && cartItem.getProduct() != null) {

                    int price = cartItem.getProduct().getPPrice();
                    cartItem.setCPrice(price * cartItem.getQuantity());
                }

                // DTO로 변환하여 리스트에 추가
                dtoList.add(CartDTO.toDTO(cartItem));
            }

            // DB에 변경사항 한 번만 저장
            crepo.saveAll(cartItems); // 가격 업데이트한 모든 CartEntity를 한 번에 저장
        }, () -> {
            System.out.println("회원 정보가 없습니다: CId = " + CId);
        });

        return dtoList;
    }

    /**
     * 장바구니에 상품 추가
     */
    public ModelAndView addCart(int pNum) {
        mav = new ModelAndView();
        String CId = (String) session.getAttribute("loginId");

        try {
            // 로그인된 회원 정보 조회
            Optional<MemberEntity> member = mrepo.findById(CId);
            if (member.isEmpty()) {
                mav.setViewName("errorPage");
                return mav;
            }

            // 제품 정보 조회
            Optional<ProductEntity> productEntity = prepo.findById(pNum);
            if (productEntity.isEmpty()) {
                System.out.println("제품 정보 조회 실패: Product ID = " + pNum);
                mav.setViewName("errorPage");
                return mav;
            }

            // 회원의 장바구니 항목 조회
            List<CartEntity> cartItems = crepo.findByMember_MId(CId);
            CartEntity existingCartItem = null;

            // 동일한 제품이 이미 장바구니에 있는지 확인
            for (CartEntity cart : cartItems) {
                if (cart.getProduct().getPNum() == pNum) {
                    existingCartItem = cart;
                    break;
                }
            }

            if (existingCartItem != null) {  // 동일한 제품이 있으면 수량 증가
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                existingCartItem.setCPrice(existingCartItem.getProduct().getPPrice() * existingCartItem.getQuantity());
                crepo.save(existingCartItem);  // 장바구니 항목 업데이트
            } else {  // 새로운 제품이면 새 항목 추가
                CartEntity cart = new CartEntity();
                cart.setMember(member.get()); // 회원 정보 설정
                cart.setProduct(productEntity.get()); // 상품 정보 설정
                cart.setCPName(productEntity.get().getPName()); // 기존 productEntity에서 이름 설정
                cart.setCPrice(productEntity.get().getPPrice()); // 기존 productEntity에서 가격 설정
                cart.setQuantity(1);  // 초기 수량 설정
                cartItems.add(cart);  // 장바구니 항목 리스트에 추가

                crepo.save(cart);  // 새 항목 저장
            }

            mav.setViewName("redirect:/cart");  // 장바구니 페이지로 리다이렉트
        } catch (Exception e) {
            System.out.println("장바구니 추가 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return mav;
    }

    /*
     * 장바구니 항목 삭제
     */
    public ModelAndView removeCartItem(int CNum) {
        mav = new ModelAndView();

        crepo.deleteById(CNum);

        mav.setViewName("redirect:/cart");
        mav.addObject("Cnum", CNum);

        return mav;
    }

    /*
     * 장바구니 비우기
     */
    public ModelAndView deleteCart(CartDTO cart) {

        mav = new ModelAndView();
        crepo.deleteAll();
        mav.setViewName("redirect:/cart");
        mav.addObject("cart", cart);
        return mav;
    }

    /*
     장바구니 항목 수량 업데이트
   */
    @Transactional
    public ModelAndView updateQuantity(int CNum, int Quantity) {
        // CNum으로 장바구니 항목 조회
        List<CartEntity> cartList = crepo.findByCNum(CNum);

        // 조회된 카트 항목이 없으면 처리
        if (cartList.isEmpty()) {
            System.out.println("조회된 카트 항목이 없습니다.");
            // 필요한 경우 예외를 던지거나 메시지를 반환할 수 있습니다.
            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:/cart");  // 장바구니로 리디렉션
            return mav;
        }
        // 여러 항목에 대해 수량 업데이트
        ModelAndView mav = new ModelAndView();
        for (CartEntity cart : cartList) {
            cart.setQuantity(Quantity);  // 수량 업데이트
            crepo.save(cart);  // 엔티티 저장
        }

        // 장바구니 페이지로 리디렉션
        mav.setViewName("redirect:/cart");
        return mav;
    }
}