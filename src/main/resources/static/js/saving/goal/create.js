// 금액 콤마 찍기
function formatNumberToCurrency(value) {
    // 정수 부분과 소수점 부분을 분리합니다. (소수점 입력을 허용하는 경우)
    let parts = value.toString().split(".");
    // 정규식을 사용하여 세 자리마다 콤마를 추가합니다.
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    // 정수 부분과 소수점 부분을 다시 합칩니다.
    return parts.join(".");
}

document.addEventListener("DOMContentLoaded", function () {
    // 'goalPrice' 입력 필드를 선택합니다.
    const goalPriceInput = document.getElementById("goalPrice");

    // 'input' 이벤트에 대한 이벤트 리스너를 추가합니다.
    goalPriceInput.addEventListener("input", function () {
        // 사용자 입력에서 콤마, 공백, 기타 문자를 제거합니다.
        let cleanedInput = this.value.replace(/[, ]+/g, "");
        // 숫자 형식으로 변환한 값을 입력 필드에 다시 설정합니다.
        this.value = formatNumberToCurrency(cleanedInput);
    });
    // 'currentPrice' 입력 필드에 대한 처리를 추가합니다.
    const currentPriceInput = document.getElementById("currentPrice");
    currentPriceInput.addEventListener("input", function () {
        let cleanedInput = this.value.replace(/[, ]+/g, "");
        this.value = formatNumberToCurrency(cleanedInput);
    });
});

function formatNumberToCurrency(value) {
    let parts = value.replace(/\D/g, '').split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
}

document.addEventListener("DOMContentLoaded", function () {
    const inputs = document.querySelectorAll('#goalPrice, #currentPrice');

    inputs.forEach(input => {
        input.addEventListener("input", function () {
            // 숫자와 콤마를 제외한 모든 문자 제거
            let numericValue = this.value.replace(/[^\d,]/g, '');
            this.value = formatNumberToCurrency(numericValue);
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("savingGoalForm");

    form.addEventListener("submit", function (event) {
        // 콤마를 제거할 필드를 선택합니다.
        const goalPriceInput = document.getElementById("goalPrice");
        const currentPriceInput = document.getElementById("currentPrice");


        // 콤마를 제거합니다.
        goalPriceInput.value = goalPriceInput.value.replace(/,/g, '');
        currentPriceInput.value = currentPriceInput.value.replace(/,/g, '');
    });
});


document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('savingGoalForm');

    form.addEventListener('submit', function (event) {
        const goalPriceInput = document.getElementById('goalPrice');
        const currentPriceInput = document.getElementById('currentPrice');

        // 콤마를 제거한 숫자만 비교를 위해 추출
        const goalPrice = parseInt(goalPriceInput.value.replace(/,/g, ''), 10);
        const currentPrice = parseInt(currentPriceInput.value.replace(/,/g, ''), 10);

        // 저축 목표 금액과 저축 현재 금액 비교
        if (currentPrice > goalPrice) {
            // 저축 현재 금액이 목표 금액보다 클 경우, 폼 제출을 방지하고 사용자에게 경고
            alert('저축 현재 금액은 저축 목표 금액을 초과할 수 없습니다.');
            event.preventDefault(); // 폼 제출 방지
            // 알람 후에도 입력 필드의 형식을 유지
            goalPriceInput.value = formatNumberToCurrency(goalPriceInput.value.replace(/,/g, ''));
            currentPriceInput.value = formatNumberToCurrency(currentPriceInput.value.replace(/,/g, ''));
        } else {
            // 폼 제출 시 콤마 제거
            goalPriceInput.value = goalPriceInput.value.replace(/,/g, '');
            currentPriceInput.value = currentPriceInput.value.replace(/,/g, '');
        }
    });
});

