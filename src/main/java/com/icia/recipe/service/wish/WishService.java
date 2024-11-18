package com.icia.recipe.service.wish;

import com.icia.recipe.dao.MemberRepository;
import com.icia.recipe.dao.ProductRepository;
import com.icia.recipe.dao.RecipeRepository;
import com.icia.recipe.dao.WishRepository;
import com.icia.recipe.dto.Member.MemberEntity;
import com.icia.recipe.dto.Order.ProductEntity;
import com.icia.recipe.dto.Order.RecipeEntity;
import com.icia.recipe.dto.Order.WishDTO;
import com.icia.recipe.dto.Order.WishEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * WishService: 위시리스트 관련 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wrepo;
    private final MemberRepository mrepo;
    private final ProductRepository prepo;
    private final RecipeRepository recrepo;
    private final HttpSession session;
    private ModelAndView mav;

    // wish: 현재 로그인한 사용자의 위시리스트를 가져오는 메소드
    public List<WishDTO> wish() {
        String WMId = (String) session.getAttribute("loginId");
        if (WMId == null) {
            // 로그인되지 않은 경우
            return null; // 또는 빈 리스트 반환 가능
        }

        // 회원 아이디를 기반으로 WishEntity 목록 조회
        List<WishEntity> entities = wrepo.findByMember_MId(WMId);
        List<WishDTO> wishList = new ArrayList<>();

        if (!entities.isEmpty()) {
            for (WishEntity entity : entities) {
                // WishEntity를 WishDTO로 변환 후 리스트에 추가
                WishDTO wish = WishDTO.toDTO(entity);
                wishList.add(wish);
            }
        }
        return wishList; // 변환된 WishDTO 리스트 반환
    }

    // addWishProduct: 상품을 위시리스트에 추가하는 메소드
    public ModelAndView addWishProduct(int pNum) {
        ModelAndView mav = new ModelAndView();

        // 현재 로그인한 사용자 확인
        String MId = (String) session.getAttribute("loginId");
        if (MId == null) {
            throw new EntityNotFoundException("로그인된 사용자가 없습니다.");
        }

        // 회원 조회
        MemberEntity member = mrepo.findById(MId)
                .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

        // 상품 조회
        ProductEntity product = prepo.findById(pNum)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // 기본 레시피 정보 설정
        RecipeEntity recipe = new RecipeEntity();
        recipe.setRNum(0);
        recipe.setRTitle("x");
        recipe.setRImageFileName("x");

        // 기존 위시리스트에 동일 상품이 존재하는지 확인
        Optional<WishEntity> existingWish = wrepo.findByMemberAndProduct(member, product);
        if (existingWish.isPresent()) {
            // 이미 찜한 상품인 경우 메시지 설정 후 반환
            mav.setViewName("WishList");
            mav.addObject("message", "이미 찜한 상품입니다.");
            return mav;
        }

        // WishEntity 생성 및 속성 설정
        WishEntity wishEntity = new WishEntity();
        wishEntity.setMember(member);
        wishEntity.setCRDate(LocalDateTime.now());
        if (pNum != 0) {
            // 상품 정보 설정
            wishEntity.setProduct(product);
            wishEntity.setRecipe(null);
            wishEntity.setWPName(product.getPName());
            wishEntity.setWPrice(product.getPPrice());
            wishEntity.setWImgUrl("x");
            wishEntity.setWRTitle("x");
        }

        // WishEntity 저장
        wrepo.save(wishEntity);

        // 결과 뷰 설정
        mav.setViewName("WishList");
        mav.addObject("wish", wishEntity);
        return mav;
    }

    // removeWish: 위시리스트에서 항목을 제거하는 메소드
    public ModelAndView removeWish(int wNum) {
        mav = new ModelAndView();
        wrepo.deleteById(wNum); // 위시리스트 항목 삭제
        mav.setViewName("WishList"); // 뷰 이름 설정
        return mav;
    }

    // addWishrecipe: 레시피를 위시리스트에 추가하는 메소드
    public ModelAndView addWishrecipe(int rNum) {
        ModelAndView mav = new ModelAndView();

        // 현재 로그인한 사용자 확인
        String MId = (String) session.getAttribute("loginId");
        if (MId == null) {
            throw new EntityNotFoundException("로그인된 사용자가 없습니다.");
        }

        // 회원 조회
        MemberEntity member = mrepo.findById(MId)
                .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

        // 레시피 조회
        RecipeEntity recipe = recrepo.findById(rNum)
                .orElseThrow(() -> new EntityNotFoundException("레시피를 찾을 수 없습니다."));

        // 기존 위시리스트에 동일 레시피가 존재하는지 확인
        Optional<WishEntity> existingWish = wrepo.findByMemberAndRecipe(member, recipe);
        if (existingWish.isPresent()) {
            // 이미 찜한 레시피인 경우 뷰 설정 후 반환
            mav.setViewName("WishList");
            return mav;
        }

        // WishEntity 생성 및 속성 설정
        WishEntity wishEntity = new WishEntity();
        wishEntity.setMember(member);
        wishEntity.setCRDate(LocalDateTime.now());
        wishEntity.setRecipe(recipe);
        wishEntity.setProduct(null);
        wishEntity.setWRTitle(recipe.getRTitle());
        wishEntity.setWImgUrl(recipe.getRImageFileName());
        wishEntity.setWPrice(0);
        wishEntity.setWPName("x");

        // WishEntity 저장
        wrepo.save(wishEntity);

        // 결과 뷰 설정
        mav.setViewName("WishList");
        mav.addObject("wish", wishEntity);
        return mav;
    }
}