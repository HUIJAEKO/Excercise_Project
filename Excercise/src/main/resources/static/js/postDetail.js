function confirmDelete(id) {
    var confirmation = confirm("정말로 삭제하시겠습니까?");
    if (confirmation) {
        console.log(id);
        $.ajax({
            url: '/post/delete/' + id,
            type: 'DELETE',
            success: function(result) {
                alert("삭제가 완료되었습니다.");
                window.location.href = '/user/main';
            },
            error: function(xhr, status, error) {
                alert("삭제 처리 중 문제가 발생했습니다.");
            }
        });
    }
}
