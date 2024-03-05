document.querySelector('.delete-button').addEventListener('click', function () {
    var goalId = this.getAttribute('data-id');

    // 모달을 표시합니다.
    var modal = document.getElementById('deleteConfirmModal');
    modal.style.display = 'block';

    // "삭제" 버튼 클릭 이벤트
    document.getElementById('confirmDelete').onclick = function () {
        deleteGoal(goalId);
        modal.style.display = 'none'; // 모달을 숨깁니다.
    };

    // "취소" 버튼 클릭 이벤트
    document.getElementById('cancelDelete').onclick = function () {
        modal.style.display = 'none'; // 모달을 숨깁니다.
    };
});

function deleteGoal(goalId) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/saving/goal/delete/" + goalId, true);
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            alert("저축 목표가 성공적으로 삭제되었습니다.");
            window.location.href = '/saving/goal/list'; // 삭제되고 이동할 곳, 나중에 목록으로 조정할 것
        } else {
            alert("삭제에 실패했습니다.");
        }
    };
    xhr.onerror = function () {
        alert("요청을 처리할 수 없습니다.");
    };
    xhr.send();
}
