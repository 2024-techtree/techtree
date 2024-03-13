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

function toggleCalculator() {
    var calculatorArea = document.getElementById("calculatorArea");
    if (calculatorArea.style.display === "none") {
        calculatorArea.style.display = "block";
    } else {
        calculatorArea.style.display = "none";
    }
}

var currentPrincipal = 0;

function addAmount(amount) {
    // 버튼을 누를 때마다 해당 금액을 현재 원금에 더함
    currentPrincipal += amount;
    document.getElementById('principal').value = currentPrincipal;
}

function resetAmount() {
    // 초기화 버튼을 누를 때 현재 원금을 초기화
    currentPrincipal = 0;
    document.getElementById('principal').value = currentPrincipal;
}
function calculate() {
    var principal = parseFloat(document.getElementById('principal').value);
    var interest = parseFloat(document.getElementById('interest').value) / 100;
    var period = parseInt(document.getElementById('period').value);

    var totalAmount = principal * Math.pow(1 + interest, period);
    var interestEarned = totalAmount - principal;

    document.getElementById('result').innerHTML = `
            <p>총액: ${totalAmount.toFixed(2)} 원</p>
            <p>이자수익: ${interestEarned.toFixed(2)} 원</p>
        `;
}

function setInterest(interestRate) {
    document.getElementById('interest').value = interestRate;
}

function resetInterest() {
    document.getElementById('interest').value = '';
}

function validateForm() {
    // 필요한 유효성 검사 수행
}

function toggleCalculator() {
    var calculatorArea = document.getElementById("calculatorArea");
    if (calculatorArea.style.display === "none") {
        calculatorArea.style.display = "block";
    } else {
        calculatorArea.style.display = "none";
    }
}

function calculate() {
    var principal = parseFloat(document.getElementById('principal').value) || 0;
    var interest = parseFloat(document.getElementById('interest').value) || 0;
    var period = parseInt(document.getElementById('period').value) || 0;

    var totalAmount = principal * Math.pow(1 + interest / 100, period);
    var interestEarned = totalAmount - principal;

    // 총액과 이자수익을 돈 단위로 포맷팅하여 쉼표를 추가
    var formattedTotalAmount = new Intl.NumberFormat().format(totalAmount.toFixed(2));
    var formattedInterestEarned = new Intl.NumberFormat().format(interestEarned.toFixed(2));

    document.getElementById('result').innerHTML = `
            <p>총액: ${formattedTotalAmount} 원</p>
            <p>이자수익: ${formattedInterestEarned} 원</p>
        `;
}