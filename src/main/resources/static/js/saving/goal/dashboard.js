document.addEventListener('DOMContentLoaded', function () {
    var canvas = document.getElementById('savingGoalChart');
    var goalNames = JSON.parse(canvas.getAttribute('data-goal-names'));
    var currentPrices = JSON.parse(canvas.getAttribute('data-current-prices'));
    var goalPrices = JSON.parse(canvas.getAttribute('data-goal-prices'));

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
    var completedCurrentPrices = JSON.parse(completedCanvas.getAttribute('data-completed-current-prices'));
    var completedGoalPrices = JSON.parse(completedCanvas.getAttribute('data-completed-goal-prices'));

    var ctxCompleted = completedCanvas.getContext('2d');
    var completedChart = new Chart(ctxCompleted, {
        type: 'bar',
        data: {
            labels: completedGoalNames,
            datasets: [{
                label: '달성 비율',
                data: completedCurrentPrices.map((value, index) => (value / completedGoalPrices[index]) * 100),
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function (value) {
                            return value + "%";
                        }
                    }
                }
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            var index = context.dataIndex;
                            var currentPrice = completedCurrentPrices[index];
                            var goalPrice = completedGoalPrices[index];
                            var achievementRate = (currentPrice / goalPrice * 100).toFixed(2);

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
});
