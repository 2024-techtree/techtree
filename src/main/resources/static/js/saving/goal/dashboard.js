document.addEventListener("DOMContentLoaded", function () {
    // 1단계: 'canvas' 요소 찾기
    var canvas = document.getElementById('savingGoalChart');

    // 2단계: 데이터 속성에서 값을 읽고 JSON으로 파싱
    var goalNames = JSON.parse(canvas.getAttribute('data-goal-names'));
    var currentPrices = JSON.parse(canvas.getAttribute('data-current-prices'));
    var goalPrices = JSON.parse(canvas.getAttribute('data-goal-prices'));

    console.log(goalNames, currentPrices, goalPrices);
    // 3단계: 'canvas' 컨텍스트 가져오기
    var ctx = canvas.getContext('2d');

    // 차트 생성
    var savingGoalChart = new Chart(ctx, {
        type: 'bar', // 차트 유형 설정
        data: {
            labels: goalNames, // x축 레이블 설정
            datasets: [{
                label: '달성 비율 (%)',
                // 목표 금액 대비 현재 금액의 비율을 계산
                data: currentPrices.map((value, index) => (value / goalPrices[index]) * 100),
                backgroundColor: 'rgba(255, 206, 86, 0.5)',
                borderColor: 'rgba(255, 206, 86, 0.5)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function (value) {
                            return value + "%"; // Y축에 퍼센트 단위 추가
                        }
                    }
                }
            },
            responsive: true, // 반응형 차트 설정
            plugins: {
                tooltip: {
                    enabled: true,
                    callbacks: {
                        label: function (tooltipItem, data) {
                            // data 객체가 정의되어 있고, datasets 배열이 존재하며, tooltipItem의 datasetIndex가 datasets 배열의 범위 내에 있는지 확인
                            if (data && data.datasets && tooltipItem.datasetIndex < data.datasets.length) {
                                let dataset = data.datasets[tooltipItem.datasetIndex];
                                let label = dataset.label || '';

                                // label 문자열을 구성하기 전에 추가적인 확인을 수행
                                if (label) {
                                    label += ': ';
                                }
                                // tooltipItem의 dataIndex가 currentPrices와 goalPrices 배열의 범위 내에 있는지 확인
                                if (typeof tooltipItem.dataIndex !== 'undefined' && tooltipItem.dataIndex < currentPrices.length && tooltipItem.dataIndex < goalPrices.length) {
                                    const currentPrice = currentPrices[tooltipItem.dataIndex];
                                    const goalPrice = goalPrices[tooltipItem.dataIndex];
                                    label += `현재 금액: ${currentPrice.toLocaleString()}원, 목표 금액: ${goalPrice.toLocaleString()}원`;
                                    return label;
                                }
                            }
                            return ''; // 유효한 데이터를 얻을 수 없는 경우 빈 문자열 반환
                        }
                    },

                    displayColors: false, // 툴팁에 데이터셋 색상을 표시하지 않음
                },
                legend: {
                    display: false // 범례 숨기기
                },
                title: {
                    display: true, // 차트 제목 표시
                    text: '저축 목표 대시보드' // 차트 제목
                }
            }
        }
    });
});
