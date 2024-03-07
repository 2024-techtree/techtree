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

function formatNumberToCurrency(value) {
    let parts = value.replace(/\D/g, '').split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
}

document.addEventListener("DOMContentLoaded", function () {
    const inputs = document.querySelectorAll('#savingPrice');

    inputs.forEach(input => {
        input.addEventListener("input", function () {
            // 숫자와 콤마를 제외한 모든 문자 제거
            let numericValue = this.value.replace(/[^\d,]/g, '');
            this.value = formatNumberToCurrency(numericValue);
        });
    });

    const form = document.getElementById("recordForm");
    form.addEventListener("submit", function (event) {
        inputs.forEach(input => {
            input.value = removeCommas(input.value); // 콤마 제거 후 값 업데이트
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("recordForm");

    form.addEventListener("submit", function (event) {
        // 콤마를 제거할 필드를 선택합니다.
        const savingPriceInput = document.getElementById("savingPrice");

        // 콤마를 제거합니다.
        savingPriceInput.value = savingPriceInput.value.replace(/,/g, '');
    });
});
