<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="include/menu.jsp" flush="false"></jsp:include>

<div class="container" id="waitUser">
    <div class="row">
        <div class="col-sm-12">
            <h2>회원(미인증) 조회</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12" align="right">
            <div class="btn btn-warning" id="deleteAllPenduserBtn">전체삭제</div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="table-responsive">
                <table class="table table-bordered col-sm-12" id="pendUserList">
                    <colgroup>
                        <col width="10%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="20%">
                        <col width="5%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>클라이언트</th>
                        <th>이메일</th>
                        <th>ActivateKey</th>
                        <th>상태</th>
                        <th>만료일자</th>
                        <th>삭제</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        pendUserList();
    });
</script>
<jsp:include page="include/footer.jsp" flush="false"></jsp:include>