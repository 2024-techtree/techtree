// 모든 삭제 버튼에 대한 참조를 가져옵니다.
var deleteButtons = document.querySelectorAll('.delete-button');

// 각 삭제 버튼에 대해 이벤트 리스너를 설정합니다.
deleteButtons.forEach(function (button) {
    button.addEventListener('click', function () {
        var goalId = this.getAttribute('data-id'); // data-id 속성에서 목표 ID를 가져옵니다.

        // 모달을 표시합니다.
        var modal = document.getElementById('deleteConfirmModal');
        modal.style.display = 'block';

        // "삭제" 버튼 클릭 이벤트를 설정합니다. 이전 이벤트 리스너를 제거하고 새로운 이벤트 리스너를 추가합니다.
        var confirmDeleteButton = document.getElementById('confirmDelete');
        var newConfirmButton = confirmDeleteButton.cloneNode(true);
        confirmDeleteButton.parentNode.replaceChild(newConfirmButton, confirmDeleteButton);

        newConfirmButton.addEventListener('click', function () {
            deleteGoal(goalId);
            modal.style.display = 'none'; // 모달을 숨깁니다.
        });

        // "취소" 버튼 클릭 이벤트
        document.getElementById('cancelDelete').onclick = function () {
            modal.style.display = 'none'; // 모달을 숨깁니다.
        };
    });
});

function deleteGoal(goalId) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/saving/goal/delete/" + goalId, true);
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            alert("저축 목표가 성공적으로 삭제되었습니다.");
            window.location.reload(); // 페이지를 새로 고침하여 변경사항을 반영합니다.
        } else {
            alert("삭제에 실패했습니다.");
        }
    };
    xhr.onerror = function () {
        alert("요청을 처리할 수 없습니다.");
    };
    xhr.send();
}
