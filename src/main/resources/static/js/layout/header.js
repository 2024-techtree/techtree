var openDropdownId = null;

// 드롭다운

document.addEventListener('click', function (event) {
    // 클릭된 요소가 드롭다운 메뉴의 일부인지 확인
    var isDropdownButton = event.target.matches('.nav-btn') || event.target.closest('.nav-dropdown') !== null;

    if (!isDropdownButton && openDropdownId !== null) {
        // 활성화된 드롭다운 메뉴가 있고, 클릭된 요소가 드롭다운 메뉴의 일부가 아니라면 드롭다운을 닫는다.
        document.getElementById(openDropdownId).style.display = 'none';
        openDropdownId = null;
    }
});

function toggleDropdown(id) {
    var dropdown = document.getElementById(id);

    if (openDropdownId !== null && openDropdownId !== id) {
        // 다른 드롭다운이 열려있는 경우 닫기
        document.getElementById(openDropdownId).style.display = 'none';
    }

    dropdown.style.display = (dropdown.style.display === 'block') ? 'none' : 'block';
    openDropdownId = (dropdown.style.display === 'block') ? id : null;
}
