<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="include/menu.jsp" flush="false"></jsp:include>


<div class="container" id="user">
    <!--유저 조회-->
    <!--유저 권한 조정-->
    <div class="row">
        <div class="col-sm-12">
            <h2>회원(인증) 조회</h2>
            <div class="form-inline div-center">
                <label>검색</label>
                <input class="form-control" id="searchUserInput">
                <button id="searchUserBtn" class="btn btn-info">조회</button>
            </div>
            <br/><br/>
        </div>
        <br>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="table-responsive">
                <table class="table table-bordered" style="display: none" id="userList">
                    <colgroup>
                        <col width="5%">
                        <col width="10%">
                        <col width="15%">
                        <col width="10%">
                        <col width="15%">
                        <col width="20%">
                        <col width="7%">
                        <col width="8%">
                        <col width="20%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>ID</th>
                        <th>이메일</th>
                        <th>이름</th>
                        <th>전화번호</th>
                        <th>주소</th>
                        <th>회사</th>
                        <th>가입일</th>
                        <th>클라이언트:스코프</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colspan="8" align="center">조회 버튼을 눌러주세요.</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row div-center">
            <nav aria-label="Page navigation" id="userListPagination">
                <ul class="pagination"></ul>
            </nav>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="updateUserClientModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title"><span id="updateUserScopeTitle"></span>스코프 정보</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="userScopeList">
                                    <colgroup>
                                        <col width="15%">
                                        <col width="35%">
                                        <col width="35%">
                                        <col width="15%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>선택</th>
                                        <th>스코프</th>
                                        <th>설명</th>
                                        <th>기본값<br>여부</th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="userList();">확인</button>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="include/footer.jsp" flush="false"></jsp:include>