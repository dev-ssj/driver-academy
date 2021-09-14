<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강사 수정</title>
<!-- Bootstrap CSS -->
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/teacherCss/teacherStyle.css" type="text/css">
<script src="https://use.fontawesome.com/747c3ff62f.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//핸드폰번호 정규식
	var regExpPhone =/(01[016789])[-]([1-9]{1}[0-9]{2,3})[-]([0-9]{4})$/;
	//이메일 정규식 : 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식
	var regExpEmail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	
	
	//폼 유효성 체크
	$('#teacher_update_form').submit(function(){
		if($('#name').val().trim()==''){
			alert('이름을 입력해주세요!');
			$('#name').focus();
			$('#name').val('');
			return false;
		}
		
		if($('#phone').val().trim()==''){
			alert('연락처를 입력해주세요!');
			$('#phone').focus();
			$('#phone').val('');
			return false;
		}else if(!regExpPhone.test($('#phone').val().trim())){ //정규식을 이용한 핸드폰번호 유효성 검사
			alert('연락처 형식이 일치하지 않습니다.');
			return false;
		}
		
		if($('#email').val().trim()==''){
			alert('이메일 주소를 입력해주세요!');
			$('#email').focus();
			$('#email').val('');
			return false;
		}else if(!regExpEmail.test($('#email').val().trim())){	//정규식을 이용한 이메일 유효성 검사
			alert('이메일 형식이 일치하지 않습니다.');
			return false;
		}
	});
	
	//이미지 미리 보기
	var photo_path;
	var my_photo;
	$('#photo').change(function(){
		$('.my-photo2').show();
		var photo = document.getElementById('photo');
		my_photo2 = photo.files[0];
		if(my_photo2){
			var reader = new FileReader();
			reader.readAsDataURL(my_photo2);
			
			reader.onload=function(){
				photo_path = $('.my-photo2').attr('src');//이미지 미리보기 하기 전 이미지 저장
				$('.my-photo2').attr('src',reader.result);
			};
		}
	});//end of change

});
</script>
</head>
<body>
<!-- header -->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<!-- menu -->
<jsp:include page="/WEB-INF/views/common/menu.jsp"/>
<div id="main-width">
<div id="menuinfo">
	강사 정보 수정
</div>

<form id="teacher_update_form" action="update.do" method="post" enctype="multipart/form-data">
<input type="hidden" id="teacher_num" name="teacher_num" value="${teacher.teacher_num}">
<c:if test="${empty teacher.teacher_profile}">
	<div class="photo-div">
	<img src="${pageContext.request.contextPath}/images/teacher/No-Image.png" width="260px" height="310px" class="my-photo2">
	</div>
</c:if>
<c:if test="${!empty teacher.teacher_profile}">
	<div class="photo-div">
		<img src="${pageContext.request.contextPath}/upload/${teacher.teacher_profile}" width="260px" height="310px" class="my-photo2">
	</div>
</c:if>

<div class="filebox" align="center">
  	<label for="photo">사진 업로드</label>
  	<input type="file" id="photo" name="photo" accept="image/gif,image/png,image/jpeg">
</div>
	<div align="center" style="width: 300px; margin: 0 auto;" >
		<input type="text" class="form-control input_width" id="name" name="name" value="${teacher.teacher_name}" placeholder="이름을 입력해 주세요">
		<input type="text" class="form-control input_width" id="phone" name="phone" value="${teacher.teacher_phone}" placeholder="하이픈(-) 포함 연락처를 입력해 주세요">
		<input type="email" class="form-control input_width" id="email" name="email" value="${teacher.teacher_email}" placeholder="이메일 주소를 입력해주세요">
	</div>
	<div align="center">
		<button type="submit" id="join-submit" class="btn btn-primary">정보수정<i class="fa fa-check spaceLeft" style="color:white; margin-left: 3px;"></i></button>
		<button type="button" class="btn btn-warning" onclick="location.href='${pageContext.request.contextPath}/teacher/list.do'">목록으로<i class="fa fa-times spaceLeft" style="color:white;margin-left: 3px;"></i></button>
	</div>

</form>

</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>