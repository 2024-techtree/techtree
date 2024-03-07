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
