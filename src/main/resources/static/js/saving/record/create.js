// 목표 이름이 변경될 때 호출되는 함수
function fetchAndSetGoalType() {
    var goalName = $("#goalName").val(); // 선택된 목표 이름 가져오기
    var baseUrl = $("#baseUrl").val(); // 현재 페이지의 URL 가져오기

    // AJAX를 사용하여 서버에 요청
    $.get(baseUrl + "saving/goal/fetchGoalType?goalName=" + goalName, function (data) {
        // 응답을 받아와서 목표 유형 필드에 설정
        $("#goalType").val(data.goalType);
    });
}

function validateForm() {
    var goalName = document.getElementById("goalName").value;

    if (goalName === "-") {
        alert("저축 목표 이름을 선택해주세요.");
        return false; // 폼 전송 중단
    }

    return true; // 폼 전송 계속 진행
}
