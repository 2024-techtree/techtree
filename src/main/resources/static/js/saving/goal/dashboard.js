document.addEventListener('DOMContentLoaded', function () {
    var canvas = document.getElementById('savingGoalChart');
    var goalNames = JSON.parse(canvas.getAttribute('data-goal-names'));
    var currentPrices = JSON.parse(canvas.getAttribute('data-current-prices'));
    var goalPrices = JSON.parse(canvas.getAttribute('data-goal-prices'));

    while (goalNames.length < 5) {
        goalNames.push(`샘플 목표 ${goalNames.length + 1}`);
        currentPrices.push(20); // 샘플 데이터로 현재 금액 0 추가
        goalPrices.push(100); // 샘플 데이터로 목표 금액 100 추가
    }

    var ctx = canvas.getContext('2d');
    var chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: goalNames,
            datasets: [{
                label: '달성 비율',
                data: currentPrices.map((value, index) => (value / goalPrices[index]) * 100),
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100, // y축 최대값을 100%로 설정
                    min: 0,   // y축 최소값을 0%로 설정
                    stepSize: 10, // y축 눈금 간격을 10% 단위로 설정
                    ticks: {
                        callback: function (value) {
                            return value + "%"
                        }
                    }
                }
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            var index = context.dataIndex;
                            var currentPrice = currentPrices[index];
                            var goalPrice = goalPrices[index];
                            var achievementRate = (currentPrice / goalPrice * 100).toFixed(2); // 소수점 둘째 자리까지

                            return [
                                `달성 비율: ${achievementRate}%`,
                                `목표 금액: ${goalPrice.toLocaleString()}원`,
                                `현재 금액: ${currentPrice.toLocaleString()}원`
                            ];
                        }
                    },
                    displayColors: false
                }
            }
        }
    });
    // "완료" 상태의 저축 목표 데이터를 위한 변수 선언 및 데이터 바인딩
    var completedCanvas = document.getElementById('completedGoalChart'); // 새로운 canvas 요소의 ID
    var completedGoalNames = JSON.parse(completedCanvas.getAttribute('data-completed-goal-names'));


    var ctxCompleted = completedCanvas.getContext('2d');
    var completedChart = new Chart(ctxCompleted, {
        type: 'doughnut', // 원형 차트 유형을 'doughnut'으로 설정
        data: {
            labels: completedGoalNames, // 완료된 목표 이름을 레이블로 사용
            datasets: [{
                label: '달성 비율',
                data: completedGoalNames.map(() => 100), // 모든 완료된 목표의 달성 비율을 100%로 설정
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true, // 차트 크기를 컨테이너에 맞춤
            plugins: {
                legend: {
                    position: 'top', // 범례 위치를 상단으로 설정
                },
                title: {
                    display: true,
                    text: '완료된 저축 목표' // 차트 제목 설정
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            // 툴팁에 표시될 텍스트를 설정합니다.
                            return `: 달성 비율 100%`;
                        }
                    }
                }
            }
        }
    });
});
