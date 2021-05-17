<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ include file="../common/head.jspf"%>



<script>
let ArticleModify__submited = false;
function ArticleModify__checkAndSubmit(form) {
	if ( ArticleModify__submited ) {
		alert('처리중입니다.');
		return;
	}
	
	form.title.value = form.title.value.trim();

	if ( form.title.value.length == 0 ) {
		alert('제목을 입력해주세요.');
		form.title.focus();

		return false;
	}

	form.body.value = form.body.value.trim();

	if ( form.body.value.length == 0 ) {
		alert('내용을 입력해주세요.');
		form.body.focus();

		return false;
	}



	ArticleModify__submited = true;

	startUploadFiles(startSubmitForm);
}
</script>


	<div class="section section-article-modify">
		<div class="container mx-auto">
	    <form method="POST" action="doModify" onsubmit="ArticleModify__checkAndSubmit(this); return false;">
	        <input type="hidden" name="id" value="${article.id}" />
	        <div class="form-control">
                <label class="label">
                    제목
                </label>
                <input value="${article.title}" class="input input-bordered w-full" type="text" maxlength="100" name="title" placeholder="제목을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    내용
                </label>
                <textarea class="textarea textarea-bordered w-full h-24" placeholder="내용을 입력해주세요." name="body" maxlength="2000">${article.body}</textarea>
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" href="#" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-save"></i></span>
                    &nbsp;
                    <span>수정</span>
                </button>

                <a href="#" class="btn btn-sm mb-1" title="자세히 보기">
                    <span><i class="fas fa-list"></i></span>
                    &nbsp;
                    <span>리스트</span>
                </a>
            </div>
	    </form>
	</div>
	</div>



<%@ include file="../common/foot.jspf"%>