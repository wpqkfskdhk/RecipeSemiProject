function pay() {
    // 아임포트 결제 객체 초기화
    let IMP = window.IMP;
    IMP.init("imp65742330");

    // 구매자 정보 설정
    let buyer_name = document.querySelector('#orderReceiver').textContent || "홍길동"; // 구매자 이름
    let hp = document.querySelector('#orderPhone').textContent || "010-1234-5678"; // 구매자 전화번호
    let addr = document.querySelector('#orderAddress').textContent || "Seoul, Korea"; // 구매자 주소

    // 총 결제 금액 가져오기
    let amountText = document.querySelector('#finalPrice').textContent;
    let amount = parseInt(amountText.replace(/[^0-9]/g, ''), 10); // 숫자만 추출하여 정수로 변환

    // 결제 요청
    IMP.request_pay({
        pg: 'kakaopay', // 결제 PG사
        pay_method: 'card', // 결제 방법
        merchant_uid: 'merchant_' + new Date().getTime(), // 고유 주문 번호
        name: "상품 결제", // 결제 상품명 (예: "주문 결제")
        amount: amount, // 결제 금액
        buyer_name: buyer_name, // 구매자 이름
        buyer_tel: hp, // 구매자 전화번호
        buyer_addr: addr, // 구매자 주소
    }, function (rsp) {
        if (rsp.success) {
            $.ajax({
                type : 'POST',
                url :"/completePay",
                success : function (result) {
                    $("#cart-items").html("");  // 장바구니 항목을 비움
                    $("#total-price").text("0원");  // 총 가격도 0원으로 초기화
                    $("#final-price").text("0원");  // 최종 가격도 0원으로 초기화
                    alert('결제가 완료되었습니다!');
                    location.href="/orderComplete"
                }
            })
        } else {
            console.log('결제 실패', rsp);
            alert('결제에 실패했습니다. 다시 시도해 주세요.');
            // 결제 실패 시 추가 처리 코드
        }
    });
}