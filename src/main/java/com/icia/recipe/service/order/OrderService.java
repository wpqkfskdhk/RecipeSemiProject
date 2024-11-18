package com.icia.recipe.service.order;

import com.icia.recipe.dao.CartRepository;
import com.icia.recipe.dao.MemberRepository;
import com.icia.recipe.dao.OrderRepository;
import com.icia.recipe.dto.Member.MemberEntity;
import com.icia.recipe.dto.Order.CartEntity;
import com.icia.recipe.dto.Order.OrderDTO;
import com.icia.recipe.dto.Order.OrderEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orepo;    // 주문 Repository
    private final MemberRepository mrepo;  // 회원 Repository
    private final CartRepository crepo;    // 장바구니 Repository
    private final HttpSession session;     // HttpSession 객체

    /**
     * 주문 이력 생성 및 조회
     *
     * @return List<OrderDTO> 생성된 주문 데이터 목록
     */
    @Transactional
    public List<OrderDTO> orderhistory() {
        List<OrderDTO> dtoList = new ArrayList<>();

        // 현재 로그인한 회원의 ID를 세션에서 가져옴
        String MId = (String) session.getAttribute("loginId");

        // 회원 정보 조회
        Optional<MemberEntity> memberOpt = mrepo.findById(MId);

        // 장바구니 항목 조회
        List<CartEntity> cartItems = crepo.findByMember_MId(MId);

        if (memberOpt.isPresent()) {
            MemberEntity member = memberOpt.get();
            int totalPrice = 0;
            int totalQuantity = 0;

            // 장바구니 항목을 순회하면서 총 가격 및 총 수량 계산
            for (CartEntity item : cartItems) {
                totalPrice += item.getCPrice();        // 총 가격 누적
                totalQuantity += item.getQuantity();  // 총 수량 누적
            }

            // 주문 정보 생성
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOAmount(totalPrice);           // 총 가격 설정
            orderEntity.setQuantity(totalQuantity);       // 총 수량 설정
            orderEntity.setOAddr(member.getMAddr());      // 회원 주소 설정
            orderEntity.setOPhone(member.getMPhone());    // 회원 전화번호 설정
            orderEntity.setOReceiver(member.getMName());  // 수신자 이름 설정
            orderEntity.setOStats("배송중");              // 주문 상태 초기화
            orderEntity.setODate(LocalDateTime.now());    // 주문 날짜 설정
            orderEntity.setMember(member);               // 회원 정보와 연결

            // 주문 정보를 데이터베이스에 저장
            orepo.save(orderEntity);

            // 저장된 주문 정보를 DTO로 변환 후 리스트에 추가
            dtoList.add(OrderDTO.toDTO(orderEntity));
        }

        return dtoList; // 생성된 주문 정보 리스트 반환
    }
}