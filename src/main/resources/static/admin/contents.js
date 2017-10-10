$(function(){

    // login check
    isLogin();

    $('#logout').on('click',logout);
    $('#createClient').on('click',createClient);
    $('#updateClientSubmit').on('click', updateClient);
    $('#createScopeSubmit').on('click',createScope);
    $('#searchUserBtn').on('click', userList);
    $('#searchUserInput').keyup(function(e){
        if(e.keyCode == 13){userList();}
    });
    $('#deleteAllPenduserBtn').on('click', deleteAllPendUser);

    
});

// 회원 (인증) 조회
var userClientScopeList = {};
var p = 1;
// 회원(인증) 조회 페이지 함수
var paginationUserList = function(page){
    p = page;
    userList();
};
var userList = function(){
    var search = $('#searchUserInput').val() === undefined ? '' : $('#searchUserInput').val();
    $('#userList tbody').empty();
    $('#userListPagination ul').empty();
    fnAjax({
        url: '/v1/users?search=' + (search === undefined ? '' : search) + '&p=' + p,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success: function(data){
            var pageInfo = data.pageInfo;
            var userList = data.userList;
            $('#userList').show();
            if(userList === undefined || userList.length == 0){
                $('#userList tbody').html('<td colspan="8" align="center">조회 결과가 없습니다.</td>');
            }else{
                $(userList).each(function(i,o){
                    var t = '<tr>';
                    t += '  <td class="td-center">';
                    t +=        o.userId;
                    t += '  </td>';
                    t += '  <td class="td-center">';
                    t +=        o.email;
                    t += '  </td>';
                    t += '  <td class="td-center">';
                    t +=        o.name;
                    t += '  </td>';
                    t += '  <td class="td-center">';
                    t +=        o.phone;
                    t += '  </td>';
                    t += '  <td>';
                    t +=        o.address;
                    t += '  </td>';
                    t += '  <td class="td-center">';
                    t +=        o.company;
                    t += '  </td>';
                    t += '  <td class="td-center" style="padding:0px;">';
                    t +=        o.registerDate.replace(' ','<br/>');
                    t += '  </td>';
                    t += '  <td class="td-center" style="padding:0px; vertical-align: middle;">';
                    var socpeList = o.scopeList;
                    var scopeListSize = socpeList.length;
                    var scopeAppendList = {};
                    for(var i=0; i<scopeListSize; i++){
                        if(scopeAppendList[socpeList[i].clientId] === undefined){
                            if(Object.keys(scopeAppendList).length >= 1){
                                t += '<hr style="padding:0px;margin:0px;">';
                            }
                            t += '<a href="javascript:void(0);" onclick="updateUserScopeModal(\''
                                + o.userId + '\',\''
                                + socpeList[i].clientId
                                + '\');">'
                                + socpeList[i].clientId
                                + '</a>'
                                + ' : ';
                            t += socpeList[i].scopeId;
                            scopeAppendList[socpeList[i].clientId]  = [];
                            scopeAppendList[socpeList[i].clientId].push(socpeList[i].scopeId);
                        }else{
                            scopeAppendList[socpeList[i].clientId].push(socpeList[i].scopeId);
                            t += ', ' + socpeList[i].scopeId;
                        }
                    }
                    t += '  </td>';
                    t += '</tr>';
                    userClientScopeList[o.userId] = scopeAppendList;
                    $('#userList tbody').append(t);
                });

                var totalPage = Math.ceil(pageInfo.userCount) % 20 == 0 ? Math.ceil(pageInfo.userCount) / 20 : (Math.ceil(pageInfo.userCount) / 20) + 1;
                var startPage = pageInfo.p - Math.ceil(pageInfo.p % 5) + 1;
                var endPage   = (startPage + 4) > totalPage ? totalPage : (startPage + 4);
                var nt = '';
                if(startPage >= 6){
                    nt += '<li><a href="javascript:void(0);" aria-label="Previous" onclick="paginationUserList(' + (startPage - 1) + ');"><span aria-hidden="true">&laquo;</span></a></li>';
                }
                for(var ni=startPage; ni < endPage; ni++){
                    if(ni == p){
                        nt += '<li class="active"><a href="javascript:void(0);">' + ni + '</a></li>';
                    }else{
                        nt += '<li><a href="javascript:void(0);" onclick="paginationUserList(' + ni + ');">' + ni + '</a></li>';
                    }
                }
                if(endPage + 1 < totalPage){
                    nt += '<li><a href="#" aria-label="Next" onclick="paginationUserList(' + (endPage + 1) + ');"><span aria-hidden="true">&raquo;</span></a></li>';
                }
                $('#userListPagination ul').html(nt);
            }
        },
        error:fnError
    });
};

var pendUserList = function(){
    $('#pendUserList tbody').empty();
    fnAjax({
        url: '/v1/pendusers',
        type: 'get',
        head:{'Authorization':getCookie('gauth')},
        success: function(data){
            var pendUserList = data;
            if(pendUserList === undefined || pendUserList.length == 0){
                $('#pendUserList tbody').html('<tr><td align="center" colspan="6">조회 된 결과가 없습니다.</td></tr>');
            }else{
                $(pendUserList).each(function(i,o){
                    var t = '';
                    t += '<tr>';
                    t += '  <td  align="center">';
                    t +=    o.clientId;
                    t += '  </td>';
                    t += '  <td>';
                    t +=    o.email;
                    t += '  </td>';
                    t += '  <td>';
                    t +=    o.activateKey;
                    t += '  </td>';
                    t += '  <td  align="center">';
                    t +=    o.status;
                    t += '  </td>';
                    t += '  <td  align="center">';
                    t +=    o.expireDate;
                    t += '  </td>';
                    t += '  <td align="center">';
                    t += '      <div class="btn btn-warning" onclick="deletePendUser(\'' + o.activateKey + '\');">삭제</div>';
                    t += '  </td>';
                    t += '</tr>';
                    $('#pendUserList tbody').append(t);
                });
            }
        },
        error:fnError
    });
};
var deletePendUser = function(activateKey){
    fnAjax({
        url: '/v1/pendusers/' + activateKey,
        type: 'delete',
        head:{'Authorization':getCookie('gauth')},
        success: function(data){
            console.log(data);
            pendUserList();
        },
        error:fnError
    });
}
var deleteAllPendUser = function(){
    fnAjax({
        url: '/v1/pendusers',
        type: 'delete',
        head:{'Authorization':getCookie('gauth')},
        success: function(data){
            console.log(data);
            pendUserList();
        },
        error:fnError
    });
};

var updateUserScope = function(obj){
    var userId = $(obj).data('user');
    var clientId = $(obj).data('client');
    var scopeId = $(obj).data('scope');
    var isChecked = $(obj).is(':checked');
    if(isChecked){
        fnAjax({
            url: '/v1/userClientScope/' + userId + '?clientId=' + clientId,
            type: 'post',
            head:{'Authorization':getCookie('gauth')},
            data: {scopeId: scopeId},
            success: function(data){
                console.log(data);
            },
            error:fnError
        });
    }else{
        fnAjax({
            url: '/v1/userClientScope/' + userId + '?clientId=' + clientId + '&scopeId=' + scopeId,
            type: 'delete',
            head:{'Authorization':getCookie('gauth')},
            success: function(data){
                console.log(data);
            },
            error:fnError
        });
    }
    return false;
};
//유저의 클라이언트 권한 조회 모달
var updateUserScopeModal = function(userId, clientId){
    $('#updateUserScopeTitle').html(userId + '의 ' + clientId);
    $('#userScopeList tbody').empty();
    fnAjax({
        url:'/v1/scopes?client=' + clientId,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){
            $(data).each(function(i,o){
                var scopes = userClientScopeList[userId][clientId];
                var inputTag = '  <input type="checkbox" name="choiceScope" data-user="' + userId + '" data-client="' + o.clientId + '" data-scope="' + o.scopeId + '" onchange="updateUserScope(this);">';
                if(scopes !== undefined){
                    var scopeSize = scopes.length;
                    for(var i=0; i < scopeSize; i++){
                        if(scopes[i] == o.scopeId){
                            inputTag = '  <input type="checkbox" name="choiceScope" checked="checked" onchange="updateUserScope(this);" data-user="' + userId + '" data-client="' + o.clientId + '" data-scope="' + o.scopeId + '">';
                            break;
                        }
                    }
                }
                var t = '';
                t += '<tr>';
                t += '<td align="center">';
                t += inputTag;
                t += '</td>';
                t += '<td align="center">';
                t += o.scopeId;
                t += '</td>';
                t += '<td>';
                t += o.description;
                t += '</td>';
                t += '<td align="center">';
                t +=    o.isDefault == 1 ? '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>' : '';
                t += '</td>';
                t += '</tr>';
                $('#userScopeList tbody').append(t);
            });
        },
        error:fnError
    });
    $('#updateUserClientModal').modal('show');
};
// 클라이언트 추가
var createClient = function(){
    var clientId = $('#c_clientId').val();
    var domain = $('#c_domain').val();
    var desc = $('#c_desc').val();
    var duCheck = false;

    $('table td:first-child').each(function(i,o){
        if($(o).text == clientId){
            duCheck = true;
            return false;
        }
    });

    if(clientId === undefined || clientId == ''){
        alert('아이디는 필수입니다.');
        return false;
    }
    if(duCheck){
        alert('아이디가 중복입니다.');
        return false;
    }
    if(domain === undefined || domain == ''){
        alert('도메인은 필수입니다.');
        return false;
    }
    fnAjax({
            url:'/v1/clients',
            type:'post',
            head:{'Authorization':getCookie('gauth')},
            data:{clientId:clientId,
                  domain:domain,
                  description:desc
            },
            success:function(data){
                $('#createClientModal input').val('');
                fnClientList();
                $('#createClientModal').modal('hide');
            },
            error:function(r,s,e){
                if(r.status == '400'){
                    alert('요정 데이터가 잘못 되었습니다.');
                }
            }
        });
};
// 클라이언트 목록 조회
var fnClientList = function(){
    $('#clientList tbody').empty();
    fnAjax({
        url:'/v1/clients',
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){
            $(data).each(function(i,o){
                var t = '<tr>';
                t += '   <td rowspan="2" style="vertical-align:middle; text-align:center;">';
                t += '   <b>';
                t +=        o.clientId
                t += '   </b>';
                t += '   </td>';
                t += '   <td>';
                t +=        o.clientSecret
                t += '   </td>';
                t += '   <td>';
                t +=        o.domain
                t += '   </td>';
                t += '   <td>';
                t +=        o.description
                t += '   </td>';
                t += '   <td align="center">';
                t +=        '<a href="javascript:void(0);" onclick="findScopeList(\'' + o.clientId + '\', this);">열기</a>';
                t += '   </td>'
                t += '   <td align="center">';
                t +=        '<a href="javascript:void(0);" onclick="updateClientModal(\'' + o.clientId + '\');">수정</a>';
                t += '   </td>'
                t += '   <td align="center">';
                t +=        '<a href="javascript:void(0);" onclick="removeClient(\'' + o.clientId + '\');">삭제</a>';
                t += '   </td>'
                t += '</tr>';
                t += '<tr>';
                t += '<td class="scopeDiv" colspan="6" data-client-id="' + o.clientId + '">';
                t += '<div class="col-sm-12">';
                t += '<h4>';
                t += '    <span id="scopeTItle"></span> 스코프';
                t += '    <button class="btn btn-success" style="float:right;" onclick="createScopeModal(\'' + o.clientId + '\');">생성</button>';
                t += '</h4>';
                t += '    <div class="row">';
                t += '    <div class="table-responsive col-sm-12">';
                t += '        <table class="table table-bordered" id="scopeList">';
                t += '            <colgroup>';
                t += '                <col width="20%">';
                t += '                <col width="40%">';
                t += '                <col width="10%">';
                t += '                <col width="15%">';
                t += '                <col width="15%">';
                t += '            </colgroup>';
                t += '            <thead>';
                t += '            <tr>';
                t += '                <th>아이디</th>';
                t += '                <th>설명</th>';
                t += '                <th>기본값<br/>여부</th>';
                t += '                <th>수정</th>';
                t += '                <th>삭제</th>';
                t += '            </tr>';
                t += '            </thead>';
                t += '            <tbody></tbody>';
                t += '        </table>';
                t += '    </div>';
                t += '    </div>';
                t += '</div>';
                t += '</td>';
                t += '</tr>';
                $('#clientList tbody:eq(0)').append(t);
            });
            if(data === undefined || data.length == 0){
                $('#clientList tbody').html('<tr><td colspan="7" align="center">조회 된 데이터가 없습니다.</td></tr>')
            }
        },
        error:fnError
    });
};
// 스코프 추가
var createScope = function(){
    var clientId = $('#c_scope_clientId').val();
    var scopeId = $('#c_scope_scopeId').val();
    var isDefault = $('#c_scope_isDefault').is(':checked') ? 'Y' : 'N';
    var desc = $('#c_scope_desc').val();

    if(scopeId === undefined || scopeId == ''){
        alert('스코프아이디는 필수입니다.');
        return false;
    }

    $('#c_scope_clientId').val('');
    $('#c_scope_scopeId').val('');
    $('#c_scope_desc').val('');
    $('#c_scope_isDefault').attr('checked', false);

    fnAjax({
            url:'/v1/scopes',
            type:'post',
            head:{'Authorization':getCookie('gauth')},
            data:{
                clientId:clientId,
                scopeId:scopeId,
                isDefault:isDefault,
                description:desc
            },
            success:function(data){
                $('#createScopeModal').modal('hide');
                findScopeList(clientId);
            },
            error:fnError
        });
}

var createScopeModal = function(clientId){
    $('#c_scope_clientId').val(clientId);
    $('#createScopeModal').modal('show');
}


// 클라이언트의 스코프 목록 조회
var findScopeList = function(clientId, obj){

    if(obj !== undefined){
        if($('#clientList .scopeDiv[data-client-id=' + clientId + ']').css('display') != 'none'){
            // 기존 열려 있던 폼
            $('#clientList .scopeDiv[data-client-id=' + clientId + ']').hide();
            $(obj).html('열기');
            return false;
        }else{
            $('#clientList .scopeDiv[data-client-id=' + clientId + ']').show();
            $(obj).html('닫기 <span class="glyphicon glyphicon-triangle-bottom" aria-hidden="true"></span>');
        }
    }

    $('#clientList .scopeDiv[data-client-id=' + clientId + '] #scopeList tbody').empty();
    $('#clientList .scopeDiv[data-client-id=' + clientId + '] #scopeTItle').text(clientId);

    fnAjax({
        url:'/v1/scopes?client=' + clientId,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){
            $(data).each(function(i,o){
                var t = '<tr>';
                t += '<td align="center">';
                t +=    o.scopeId;
                t += '</td>';
                t += '<td>';
                t +=    o.description;
                t += '</td>';
                t += '<td align="center">';
                t +=    o.isDefault == 1 ? '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>' : '';
                t += '</td>';
                t += '<td align="center">';
                t += '  <a href="javascript:void(0);" onclick="updateScopeModal(\'' + o.scopeId + '\',\'' + clientId + '\');">수정</a>';
                t += '</td>';
                t += '<td align="center">';
                t += '  <a href="javascript:void(0);" onclick="removeScope(\'' + o.scopeId + '\',\'' + clientId + '\');">삭제</a>';
                t += '</td>';
                t += '</tr>';
                $('#clientList .scopeDiv[data-client-id=' + clientId + '] #scopeList tbody').append(t);
            });
            if(data === undefined || data.length == 0){
                $('#clientList .scopeDiv[data-client-id=' + clientId + '] #scopeList tbody').html('<tr><td colspan="5" align="center">등록된 스코프가 없습니다.</td></tr>');
            }
        }
    });
};
// 스코프 수정
var updateScopeSubmit = function(){
    var clientId = $('#u_scope_ClientId').val();
    var scopeId = $('#u_scope_scopeId').val();
    var isDefault = $('#u_scope_isDefailt').is(':checked') ? 'Y' : 'N';
    var description = $('#u_scope_desc').val();

    $('#u_scope_ClientId').val('');
    $('#u_scope_scopeId').val('');
    $('#u_scope_isDefailt').attr('checked', false);
    $('#u_scope_desc').val('');

    fnAjax({
        url:'/v1/scopes/' + scopeId,
        type:'put',
        head:{'Authorization':getCookie('gauth')},
        data:{clientId:clientId,
            isDefault:isDefault,
            description:description
        },
        success:function(data){
            $('#updateScopeModal').modal('hide');
            findScopeList(clientId);
        },
        error:fnError
    });
};

// 스코프 수정 모달
var updateScopeModal = function(scopeId, clientId){
    $('#updateScopeModal').modal('show');

    fnAjax({
        url:'/v1/scopes/' + scopeId + '?clientId=' + clientId,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){
            $('#u_scope_ClientId').val(clientId);
            $('#u_scope_scopeId').val(data.scopeId);
            $('#u_scope_desc').val(data.description);
            if(data.isDefault != 0){
                $('#u_scope_isDefailt').attr('checked',true);
            }else{
                $('#u_scope_isDefailt').attr('checked',false);
            }
        },
        error:fnError
    });

}


// 스코프 삭제
var removeScope = function(scopeId, clientId){
    if(!confirm('스코프를 삭제하시겠습니까?')){
        return false;
    }
    fnAjax({
        url:'/v1/scopes/' + scopeId + '?clientId=' + clientId,
        type:'delete',
        head:{'Authorization':getCookie('gauth')},
        complete:function(data){
            if(data.status == 200){
                findScopeList(clientId);
            }else{
                alert('삭제 실패');
                console.log(data);
            }
        }
    });

}


// 클라이언트 수정 모달 띄우기
var updateClientModal = function(clientId){
    fnAjax({
        url:'/v1/clients/' + clientId,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){
            $('#u_clientId').val(data.clientId);
            $('#u_secretKey').val(data.clientSecret);
            $('#u_domain').val(data.domain);
            $('#u_desc').val(data.description);
        }
    });
    $('#updateClientModal').modal('show');
};

var updateClient = function(){

    var clientId = $('#u_clientId').val();
    var secretKey = $('#u_secretKey').val();
    var domain = $('#u_domain').val();
    var desc = $('#u_desc').val();

    fnAjax({
        url:'/v1/clients/' + clientId,
        type:'put',
        head:{'Authorization':getCookie('gauth')},
        data: {
            clientId: clientId,
            clientSecret:secretKey,
            domain:domain,
            description:desc
        },
        success:function(data){
            fnClientList();
            $('#updateClientModal').modal('hide');
        },
        error: fnError
    });
};

//클라이언트 삭제
var removeClient = function(clientId){
    if(!confirm(clientId + '를 삭제하시겠습니까?')){
        return false;
    }
    if(clientId == $('#c_scope_clientId').val()){
        $('.scopeDiv').hide();
    }
    fnAjax({
            url:'/v1/clients/' + clientId,
            type:'delete',
            head:{'Authorization':getCookie('gauth')},
            success:function(data){
                fnClientList();
            },
            error:fnError
        });
}
//로그아웃
var logout = function(){
    var tokenId = getCookie('gauth');
    deleteCookie('gauth');
    fnAjax({
        url:'/v1/token',
        type:'delete',
        head:{'Authorization':tokenId},
        success:function(){
            loginPage();
        },
        error:function(){
            loginPage();
        }
    });
};

var isLogin = function(){
    var tokenId = getCookie('gauth');
    if(tokenId !== undefined && tokenId != ''){
        fnAjax({
            url:'/v1/validateToken',
            type:'head',
            head:{'Authorization':tokenId},
            error:function(e,x,h){
                loginPage();
            }
        });
    }else{
        loginPage();
    }
};

var fnAjax = function(req){
    var url = req.url;
    var type = req.type;
    var data = req.data;
    var head = req.head;
    var success = req.success;
    var error = req.error;
    var complete = req.complete;
    $.ajax({
        url: url,
        type: type,
        contentType:'application/json',
        data:JSON.stringify(data),
        beforeSend : function(xhr){
            for (var key in head) {
                console.log(key + ": " + head[key]);
                xhr.setRequestHeader(key, head[key]);
            }
        },
        success:success,
        error: error,
        complete: complete
    });
};

var loginPage = function(){
    location.href='/v1/admin';
}

// 쿠키 생성
var setCookie = function(name, value, expiredays) {
    var cookie = name + "=" + escape(value) + "; path=/;"
    if (typeof expiredays != 'undefined') {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        cookie += "expires=" + todayDate.toGMTString() + ";"
    }
    document.cookie = cookie;
}

// 쿠키 획득
var getCookie = function(name) {
    name += "=";
    var cookie = document.cookie;
    var startIdx = cookie.indexOf(name);
    if (startIdx != -1) {
        startIdx += name.length;
        var endIdx = cookie.indexOf(";", startIdx);
        if (endIdx == -1) {
            endIdx = cookie.length;
            return unescape(cookie.substring(startIdx, endIdx));
        }
    }
    return null;
}

// 쿠키 삭제
var deleteCookie = function(name) {
    setCookie(name, "", -1);
}
// 에러
var fnError = function(request,status,error){
    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    console.log(request,status,error);
}