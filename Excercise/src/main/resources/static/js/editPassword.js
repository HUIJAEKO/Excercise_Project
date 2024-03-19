$(document).ready(function() {
	$("#edit_password").click(function(event) {
		event.preventDefault();
		var currentPw = $("#currentPw").val();
		var newPw = $("#newPw").val();
		var newPwConfirm = $("#newPwConfirm").val();

		var confirmation = confirm("정보를 수정하시겠습니까?");
		if (confirmation) {
			$.ajax({
				url: '/user/editPassword',
				type: 'PATCH',
				contentType: 'application/json',
				data: JSON.stringify({
					currentPw: currentPw,
					newPw: newPw,
					newPwConfirm: newPwConfirm
				}),
				success: function (response) {
					alert("비밀번호가 성공적으로 변경되었습니다.");
					window.location.href = "/user/logout";

				},
				error: function (xhr, status, error) {
					var errorMessage = xhr.responseJSON.error;
					if (errorMessage.includes("현재 비밀번호가 일치하지 않습니다.")) {
						$("#currentPw-error-message").text(errorMessage);
					} else if (errorMessage.includes("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.")) {
						$("#newPwConfirm-error-message").text(errorMessage);
					}
					setTimeout(function () {
						$("#currentPw-error-message").empty();
						$("#newPwConfirm-error-message").empty();
					}, 1000);
				}
			});
		}
	});
});
