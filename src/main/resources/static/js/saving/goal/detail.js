document.querySelector('.delete-button').addEventListener('click', function () {
    var goalId = this.getAttribute('data-id'); // 버튼의 data-id 속성에서 goalId를 가져옵니다.
    if (!goalId) {
        alert('저축 목표 ID가 존재하지 않습니다.');
        return;
    }
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/saving/goal/delete/goal/" + goalId, true);
    xhr.onload = function () {
        console.log('Status:', xhr.status); // 서버로부터 받은 상태 코드 로깅
        console.log('Response:', xhr.responseText); // 서버로부터 받은 응답 본문 로깅

        if (xhr.status >= 200 && xhr.status < 300) {
            alert("저축 목표가 성공적으로 삭제되었습니다.");
            window.location.href = '/'; // 성공 후 리다이렉션할 실제 페이지 경로
        } else {
            alert("삭제에 실패했습니다.");
        }
    };
    xhr.onerror = function () {
        alert("요청을 처리할 수 없습니다.");
    };
    xhr.send();
});