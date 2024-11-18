$(document).ready(function() {
    // 마이페이지 텍스트 클릭 시 드롭다운 토글
    $("#myPageText").click(function(event) {
        $("#profile-dropdown").toggle(); // 드롭다운 메뉴를 보이거나 숨기기
        event.stopPropagation(); // 클릭 이벤트가 전파되는 것을 막음
    });

    // 드롭다운 외부 클릭 시 메뉴 숨기기
    $(document).click(function(event) {
        // "마이페이지" 텍스트나 드롭다운 메뉴가 아닌 다른 곳을 클릭했을 경우
        if (!$(event.target).closest('.profile-container').length && !$(event.target).is('#myPageText')) {
            $("#profile-dropdown").hide(); // 드롭다운을 숨김
        }
    });
});