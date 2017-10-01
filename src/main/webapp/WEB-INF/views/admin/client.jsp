<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>gauth admin contents</title>
    <script src='/v1/js/jquery-3.2.1.js'></script>
    <link rel="stylesheet" href="/v1/bootstrap/css/bootstrap.min.css">
    <script src="/v1/bootstrap/js/bootstrap.min.js"></script>
    <script src="contents.js"></script>
    <style>
        .create_label {
            min-width:150px;
        }
        .create_input {
            min-width:300px;
        }
        .div-center {
            display: block;
            text-align: center;
        }
        .table-responsive th {
            text-align:center;
        }
        .td-center {
            text-align:center;
        }
        .scopeDiv {
            display: none;
        }

    </style>
</head>
<body>


<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/v1/admin">Gauth</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="javascript:void(0);">클라이언트</a></li>
            <li><a href="user.html">회원(인증)</a></li>
            <li><a href="wait-user.html">회원(미인증)</a></li>
        </ul>
    </div>
</nav>


<div role="tabpanel" class="container" id="client">
    <div class="row">
        <div class="col-sm-12">
            <h3>
                클라이언트
                <button class="btn btn-success" style="float:right;" data-toggle="modal" data-target="#createClientModal">생성</button>
            </h3>

            <div class="table-responsive">
                <table class="table table-bordered" id="clientList">
                    <colgroup>
                        <col width="20%">
                        <col width="25%">
                        <col width="15%">
                        <col width="25%">
                        <col width="5%">
                        <col width="5%">
                        <col width="5%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>아이디</th>
                        <th>시크릿키</th>
                        <th>도메인</th>
                        <th>설명</th>
                        <th>스코프</th>
                        <th>수정</th>
                        <th>삭제</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- 클라이언트 등록 모달 -->
    <div class="modal fade" id="createClientModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">클라이언트 등</h4>
                </div>
                <div class="modal-body">
                    <div class="form-inline">
                        <label class="create_label">클라이언트 아이디 *</label>
                        <input class="form-control create_input" id="c_clientId">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">클라이언트 도메인 *</label>
                        <input class="form-control create_input" id="c_domain">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">설명</label>
                        <input class="form-control create_input" id="c_desc">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-primary" id="createClient">저장</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 클라이언트 수정 모달 -->
    <div class="modal fade" id="updateClientModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">클라이언트 수정</h4>
                </div>
                <div class="modal-body">
                    <div class="form-inline">
                        <label class="create_label">아이디</label>
                        <input class="form-control create_input" disabled="disabled" id="u_clientId">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">시크릿키</label>
                        <input class="form-control create_input" disabled="disabled" id="u_secretKey">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">도메인</label>
                        <input class="form-control create_input" id="u_domain">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">설명</label>
                        <input class="form-control create_input" id="u_desc">
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-primary" id="updateClientSubmit">저장</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 스코프 등록 모달 -->
    <div class="modal fade" id="createScopeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">스코프 등록</h4>
                </div>
                <div class="modal-body">
                    <div class="form-inline">
                        <label class="create_label">클라이언트</label>
                        <input class="form-control create_input" id="c_scope_clientId" disabled="disabled">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">스코프 아이디 *</label>
                        <input class="form-control create_input" id="c_scope_scopeId">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">설명</label>
                        <input class="form-control create_input" id="c_scope_desc">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">기본값 여부</label>
                        <input type="checkbox" id="c_scope_isDefault" value="1">
                        <label for="c_scope_isDefault">사용</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-primary" id="createScopeSubmit">저장</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 스코프 수정 모달 -->
    <div class="modal fade" id="updateScopeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">스코프 수정</h4>
                </div>
                <div class="modal-body">
                    <div class="form-inline">
                        <label class="create_label">아이디</label>
                        <input type="hidden" disabled="disabled" id="u_scope_ClientId">
                        <input class="form-control create_input" disabled="disabled" id="u_scope_scopeId">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">설명</label>
                        <input class="form-control create_input" id="u_scope_desc">
                    </div>
                    <div class="form-inline">
                        <label class="create_label">기본값 여부</label>
                        <input type="checkbox" id="u_scope_isDefailt" value="1">
                        <label for="c_scope_isDefault">사용</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-primary" onclick="updateScopeSubmit();">저장</button>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>