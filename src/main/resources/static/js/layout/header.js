var openDropdownId = null;

// 드롭다운
function toggleDropdown(id) {
    var dropdown = document.getElementById(id);

    if (openDropdownId !== null && openDropdownId !== id) {
        // 다른 드롭다운이 열려있는 경우 닫기
        document.getElementById(openDropdownId).style.display = 'none';
    }

    dropdown.style.display = (dropdown.style.display === 'block') ? 'none' : 'block';
    openDropdownId = (dropdown.style.display === 'block') ? id : null;
}